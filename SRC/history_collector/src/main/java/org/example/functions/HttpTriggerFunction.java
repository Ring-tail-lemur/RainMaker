package org.example.functions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.functions.dto.LoadingDataDto;
import org.example.functions.dto.SourceDataDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.SourceDataExtractor;
import org.example.functions.extractor.SourceDataExtractorImpl;
import org.example.functions.loador.DataLoader;
import org.example.functions.loador.DataLoaderImpl;
import org.example.functions.transformer.SourceDataTransformer;
import org.example.functions.transformer.SourceDataTransformerImpl;
import org.example.functions.util.SlackLogger;
import org.example.functions.util.constants.RequestVariable;
import org.example.functions.util.exception.DataSourceAdaptorNotFindException;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class HttpTriggerFunction {

	//deployment Test1
	private SourceDataExtractor sourceDataExtractor;
	private SourceDataTransformer sourceDataTransformer;
	private DataLoader dataLoader;

	private void setObjects() {
		sourceDataExtractor = SourceDataExtractorImpl.getInstance();
		sourceDataTransformer = SourceDataTransformerImpl.getInstance();
		dataLoader = DataLoaderImpl.getInstance();
	}

	public HttpTriggerFunction() {
		setObjects();
	}

	@FunctionName("HttpExample")
	public HttpResponseMessage run(
		@HttpTrigger(name = "req",
			methods = {HttpMethod.GET, HttpMethod.POST},
			authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request) throws Exception {
		JSONArray requestParameterArray = getRequestBody(request);

		for (Object requestParameter : requestParameterArray) {
			if (requestParameter instanceof JSONObject) {
				JSONObject requestParameterJSONObject = (JSONObject)requestParameter;
				Map<String, String> requestParameterMap = getRequestParameterMap(requestParameterJSONObject);
				List<DataExtractingConfigDto> dataExtractingConfigDtoList = DataExtractingConfigDto.getDataExtractingConfigDtoList(
					requestParameterMap, RequestVariable.getRequestVariableMap(requestParameterMap));
				runPipeLine(dataExtractingConfigDtoList);
			} else {
				SlackLogger slackLogger = new SlackLogger();
				slackLogger.sendLog(new IllegalArgumentException(), "JSONObject is IllegalArgument!");
				throw new IllegalArgumentException();
			}
		}
		runAnalystService();
		return request
			.createResponseBuilder(HttpStatus.OK)
			.body("happy!")
			.build();
	}

	private void runPipeLine(List<DataExtractingConfigDto> dataExtractingConfigDtoList) throws
		DataSourceAdaptorNotFindException,
		IOException,
		ResponseTypeMissMatchException {
		for (DataExtractingConfigDto dataExtractingConfigDto : dataExtractingConfigDtoList) {
			SourceDataDto sourceDataDto = sourceDataExtractor.extractData(dataExtractingConfigDto);
			LoadingDataDto loadingDataDto = sourceDataTransformer.transformData(sourceDataDto);
			dataLoader.load(loadingDataDto);
		}
	}

	private static Map<String, String> getRequestParameterMap(JSONObject requestParameter) {
		return requestParameter.toMap().entrySet()
			.stream()
			.map(entry -> List.of(entry.getKey(), (String)entry.getValue()))
			.collect(Collectors.toMap(entry -> entry.get(0), entry -> entry.get(1)));
	}

	private static JSONArray getRequestBody(HttpRequestMessage<Optional<String>> request) {
		String body = request.getBody().orElseThrow(IllegalArgumentException::new);
		return new JSONArray(body);
	}

	private void runAnalystService() throws IOException, ParseException {
		URL url = new URL("https://github-analystics.azurewebsites.net/api/AnalystHttpTrigger");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();
		if (responseCode != 200) {
			SlackLogger slackLogger = new SlackLogger();
			slackLogger.sendLog(new RuntimeException(), "analyst 실행 실패");
			throw new RuntimeException("analyst 실행 실패!");
		}
	}
}
