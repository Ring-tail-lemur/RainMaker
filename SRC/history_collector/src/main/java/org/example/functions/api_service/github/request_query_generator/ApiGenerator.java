package org.example.functions.api_service.github.request_query_generator;

import java.net.MalformedURLException;
import java.util.List;

import org.example.functions.api_service.github.HttpRequestDto;
import org.json.JSONObject;

public interface ApiGenerator {

	public List<HttpRequestDto> getHttpRequestDtoList(JSONObject configJSONObject) throws Exception;

	public HttpRequestDto getHttpRequestDto(JSONObject configElementJSONObject, String tableName) throws
		MalformedURLException;
}
