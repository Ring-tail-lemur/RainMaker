package org.example.functions.collector.github.request_query_generator.restapi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.collector.github.request_query_generator.ApiGenerator;
import org.example.functions.dto.HttpRequestDto;
import org.example.functions.util.StringFormatter;
import org.example.functions.util.constants.RequestPathParameter;
import org.json.JSONObject;

public class GithubRestApiGenerator implements ApiGenerator {

	private final String repositoryName;
	private final String ownerName;
	private final String token;
	private final StringFormatter stringFormatter = new StringFormatter();

	public GithubRestApiGenerator(String repositoryName, String ownerName, String token) {
		this.repositoryName = repositoryName;
		this.ownerName = ownerName;
		this.token = token;
	}

	public GithubRestApiGenerator(Map<String, String> requestQueryParameters) {
		this.repositoryName = requestQueryParameters.get(RequestPathParameter.REPOSITORY_NAME);
		this.ownerName = requestQueryParameters.get(RequestPathParameter.OWNER_NAME);
		this.token = requestQueryParameters.get(RequestPathParameter.TOKEN);
	}

	public List<HttpRequestDto> getHttpRequestDtoList(JSONObject configJSONObject) {
		List<HttpRequestDto> httpRequestDtoList = new ArrayList<>();

		Iterator<String> configElementKeys = configJSONObject.keys();
		while (configElementKeys.hasNext()) {
			String configElementKey = configElementKeys.next();
			HttpRequestDto httpRequestDto = getHttpRequestDto((JSONObject)configJSONObject.get(configElementKey),
				configElementKey);
			httpRequestDtoList.add(httpRequestDto);
		}
		return httpRequestDtoList;
	}

	public HttpRequestDto getHttpRequestDto(JSONObject configElementJSONObject, String tableName) {
		JSONObject request = configElementJSONObject.getJSONObject("request");
		appendUserSpecificData(request);
		String url = stringFormatter.bindParameters(request.getString("url"), request.getJSONObject("path_parameters"))
			+ getStringQueryParameters(request.getJSONObject("query_parameters"));
		try {
			return HttpRequestDto.builder()
				.requestType(tableName)
				.url(new URL(url))
				.header(request.getJSONObject("header"))
				.method(request.getString("method"))
				.build();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private void appendUserSpecificData(JSONObject request) {
		JSONObject pathParameters = request.getJSONObject("path_parameters");
		pathParameters.put("owner", ownerName);
		pathParameters.put("repo", repositoryName);
		JSONObject header = request.getJSONObject("header");
		header.put("Authorization", String.format("Bearer %s", token));
	}

	private String getStringQueryParameters(JSONObject queryParameters) {
		StringBuilder stringQueryParameters = new StringBuilder("?");
		Iterator<String> queryParameterKeys = queryParameters.keys();
		while (queryParameterKeys.hasNext()) {
			String queryParameterKey = queryParameterKeys.next();
			stringQueryParameters.append(
				String.format("%s=%s&", queryParameterKey, queryParameters.get(queryParameterKey)));
		}
		return stringQueryParameters.substring(0, stringQueryParameters.length() - 1);
	}
}
