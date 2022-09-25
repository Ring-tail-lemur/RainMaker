package org.example.functions.collector.github.request_query_generator;

import java.net.MalformedURLException;
import java.util.List;

import org.example.functions.dto.HttpRequestDto;
import org.json.JSONObject;

public interface ApiGenerator {

	List<HttpRequestDto> getHttpRequestDtoList(JSONObject configJSONObject) throws Exception;

	HttpRequestDto getHttpRequestDto(JSONObject configElementJSONObject, String tableName) throws
		MalformedURLException;
}
