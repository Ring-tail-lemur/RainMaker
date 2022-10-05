package org.example.functions;

import java.util.List;
import java.util.Optional;

import org.example.functions.dto.LoadingDataDto;
import org.example.functions.dto.SourceDataDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.SourceDataExtractor;
import org.example.functions.extractor.SourceDataExtractorImpl;
import org.example.functions.loador.DataLoader;
import org.example.functions.loador.DataLoaderImpl;
import org.example.functions.transformer.SourceDataTransformer;
import org.example.functions.transformer.SourceDataTransformerImpl;
import org.example.functions.util.constants.RequestVariable;

import com.microsoft.azure.functions.ExecutionContext;
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
			authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) throws Exception {

		List<DataExtractingConfigDto> dataExtractingConfigDtoList = DataExtractingConfigDto.getDataExtractingConfigDtoList(
			request, RequestVariable.getRequestVariableMap(request));
		for (DataExtractingConfigDto dataExtractingConfigDto : dataExtractingConfigDtoList) {
			SourceDataDto sourceDataDto = sourceDataExtractor.extractData(dataExtractingConfigDto);
			LoadingDataDto loadingDataDto = sourceDataTransformer.transformData(sourceDataDto);
			dataLoader.load(loadingDataDto);
		}
		return request
			.createResponseBuilder(HttpStatus.OK)
			.body("happy!")
			.build();
	}
}
