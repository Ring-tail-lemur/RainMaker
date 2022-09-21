package org.example.functions.api_service.github.request_query_generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.api_service.github.HttpRequestDto;
import org.example.functions.api_service.github.request_query_generator.graphql.GithubGraphqlGenerator;
import org.example.functions.api_service.github.request_query_generator.restapi.GithubRestApiGenerator;
import org.json.JSONArray;
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

	public List<HttpRequestDto> getAllHttpRequestDtoList(JSONObject configJSONObject) throws Exception {
		List<HttpRequestDto> allHttpRequestDtoList = new ArrayList<>();
		Iterator<String> configElementKeys = configJSONObject.keys();
		while (configElementKeys.hasNext()) {
			String configElementKey = configElementKeys.next();
			List<HttpRequestDto> httpRequestDtoList = getAllHttpRequestDtoList(configJSONObject, configElementKey);
			allHttpRequestDtoList.addAll(httpRequestDtoList);
		}
		return allHttpRequestDtoList;
	}

	private List<HttpRequestDto> getAllHttpRequestDtoList(JSONObject configJSONObject, String configElementKey) throws
		Exception {
		List<HttpRequestDto> httpRequestDtoList = new ArrayList<>();
		if (configJSONObject.get(configElementKey) instanceof JSONArray) {
			JSONArray configJsonArray = configJSONObject.getJSONArray(configElementKey);
			for (int i = 0; i < configJsonArray.length(); i++) {
				JSONObject configJsonObject = configJsonArray.getJSONObject(i);
				httpRequestDtoList.add(getHttpRequestDto(configJsonObject, configElementKey));
			}
			return httpRequestDtoList;
		}
		JSONObject configJsonObject = configJSONObject.getJSONObject(configElementKey);
		httpRequestDtoList.add(getHttpRequestDto(configJsonObject, configElementKey));
		return httpRequestDtoList;
	}

	private HttpRequestDto getHttpRequestDto(JSONObject configJSONObject, String configElementKey) throws Exception {
		HttpRequestDto httpRequestDto;
		if (isRestApi(configJSONObject, configElementKey))
			httpRequestDto = githubRestApiGenerator.getHttpRequestDto(
				configJSONObject, configElementKey);
		else if (isGraphql(configJSONObject, configElementKey)) {
			httpRequestDto = githubGraphqlGenerator.getHttpRequestDto(
				configJSONObject, configElementKey);
		} else
			throw new Exception(
				String.format("%s의 요청방식은 정의되지 않은 요청 방식입니다. (현재 RestAPI와 Graphql만 지원합니다.)", configElementKey));
		return httpRequestDto;
	}

	private boolean isGraphql(JSONObject configJSONObject, String configElementKey) {
		return configJSONObject.keySet().contains("graphql");
	}

	private static boolean isRestApi(JSONObject configJSONObject, String configElementKey) {
		return configJSONObject.keySet().contains("request");
	}
}
