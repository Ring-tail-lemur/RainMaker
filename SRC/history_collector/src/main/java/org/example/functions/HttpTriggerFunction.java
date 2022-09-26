package org.example.functions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.collector.github.HttpRequestSender;
import org.example.functions.collector.github.request_query_generator.ApiGeneratorFactory;
import org.example.functions.dao.QueryRunner;
import org.example.functions.dto.HttpRequestDto;
import org.example.functions.parser.QueryGenerator;
import org.example.functions.util.ConfigReader;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class HttpTriggerFunction {
	//deployment Test
	ConfigReader configReader = ConfigReader.getConfigReader();
	HttpRequestSender httpRequestSender = new HttpRequestSender();
	QueryRunner queryRunner = QueryRunner.getQueryRunner();

	@FunctionName("HttpExample")
	public HttpResponseMessage run(
		@HttpTrigger(
			name = "req",
			methods = {HttpMethod.GET, HttpMethod.POST},
			authLevel = AuthorizationLevel.ANONYMOUS)
		HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) throws Exception {

		JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();

		ApiGeneratorFactory apiGeneratorFactory = new ApiGeneratorFactory(request.getQueryParameters());
		List<HttpRequestDto> httpRequestDtoList = apiGeneratorFactory.getAllHttpRequestDtoList(jsonObjectConfig);
		Map<String, JSONArray> responseJSONArrayList = httpRequestSender.sendAllHttpRequest(httpRequestDtoList);

		QueryGenerator queryGenerator = new QueryGenerator(jsonObjectConfig);
		List<String> queryList = queryGenerator.generateQueryList(responseJSONArrayList);
		queryRunner.runInsertQueries(queryList);

		return request.createResponseBuilder(HttpStatus.OK).body(queryList.toString()).build();
	}
}
