package org.example.functions.extractor.datainterface;

import java.io.IOException;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.datainterface.transporter.HttpRequestSender;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;

public class RestApiAdapter implements DataSourceAdapter {

	private static final RestApiAdapter restApiAdapter = new RestApiAdapter();
	private final HttpRequestSender httpRequestSender = HttpRequestSender.getInstance();

	private RestApiAdapter() {
	}

	public static RestApiAdapter getInstance() {
		return restApiAdapter;
	}
	@Override
	public boolean isAdapted(AdapterType interfaceType) {
		return interfaceType == AdapterType.RESTAPI;
	}

	@Override
	public JSONArray send(DataExtractingConfigDto dataExtractingConfigDto) throws
		IOException,
		ResponseTypeMissMatchException {
		HttpRequestDto httpRequestDto = dataExtractingConfigDto.getHttpRequestDto();
		return httpRequestSender.send(httpRequestDto);
	}
}
