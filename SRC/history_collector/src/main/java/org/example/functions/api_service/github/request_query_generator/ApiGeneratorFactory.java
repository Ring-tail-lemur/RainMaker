package org.example.functions.api_service.github.request_query_generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.api_service.github.HttpRequestDto;
import org.example.functions.api_service.github.request_query_generator.graphql.GithubGraphqlGenerator;
import org.example.functions.api_service.github.request_query_generator.restapi.GithubRestApiGenerator;
import org.json.JSONObject;

public class ApiGeneratorFactory {
	private final ApiGenerator githubRestApiGenerator;
	private final ApiGenerator githubGraphqlGenerator;

	public ApiGeneratorFactory(Map<String, String> requestQueryParameters) {
		this.githubRestApiGenerator = new GithubRestApiGenerator(requestQueryParameters);
		this.githubGraphqlGenerator = new GithubGraphqlGenerator(requestQueryParameters);
	}

	public ApiGenerator getGithubGraphqlGenerator() {
		return githubGraphqlGenerator;
	}

	public ApiGenerator getGithubRestApiGenerator() {
		return githubRestApiGenerator;
	}

	public List<HttpRequestDto> getHttpRequestDtoList(JSONObject configJSONObject) throws Exception {
		List<HttpRequestDto> httpRequestDtoList = new ArrayList<>();
		Iterator<String> configElementKeys = configJSONObject.keys();
		while (configElementKeys.hasNext()) {
			String configElementKey = configElementKeys.next();
			HttpRequestDto httpRequestDto = getHttpRequestDto(configJSONObject, configElementKey);
			httpRequestDtoList.add(httpRequestDto);
		}
		return httpRequestDtoList;
	}

	private HttpRequestDto getHttpRequestDto(JSONObject configJSONObject, String configElementKey) throws Exception {
		HttpRequestDto httpRequestDto;
		if (isRestApi(configJSONObject, configElementKey))
			httpRequestDto = githubRestApiGenerator.getHttpRequestDto(
				(JSONObject)configJSONObject.get(configElementKey),
				configElementKey);
		else if (isGraphql(configJSONObject, configElementKey)) {
			httpRequestDto = githubGraphqlGenerator.getHttpRequestDto(
				(JSONObject)configJSONObject.get(configElementKey),
				configElementKey);
		} else
			throw new Exception("정의되지 않은 요청 방식입니다. (현재 RestAPI와 Graphql만 지원합니다.)");
		return httpRequestDto;
	}

	private boolean isGraphql(JSONObject configJSONObject, String configElementKey) {
		return configJSONObject.getJSONObject(configElementKey).keySet().contains("graphql");
	}

	private static boolean isRestApi(JSONObject configJSONObject, String configElementKey) {
		return configJSONObject.getJSONObject(configElementKey).keySet().contains("request");
	}
}
