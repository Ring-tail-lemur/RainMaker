package org.example.functions.api_service.github;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.json.JSONObject;

public class GithubRestApiGenerator {

	private String repositoryName;
	private String ownerName;
	private String Token;

	public GithubRestApiGenerator(String repositoryName, String ownerName, String token) {
		this.repositoryName = repositoryName;
		this.ownerName = ownerName;
		Token = token;
	}

	public List<GithubRestApiDto> getGithubRestApiDtoList(JSONObject configJSONObject) {
		List<GithubRestApiDto> githubRestApiDtoList = new ArrayList<>();

		Iterator<String> configElementKeys = configJSONObject.keys();
		while (configElementKeys.hasNext()) {
			String configElementKey = configElementKeys.next();
			GithubRestApiDto githubRestApiDto = getGithubRestApiDto((JSONObject)configJSONObject.get(configElementKey));
			githubRestApiDtoList.add(githubRestApiDto);
		}
		return githubRestApiDtoList;
	}

	public GithubRestApiDto getGithubRestApiDto(JSONObject configElementJSONObject) {
		JSONObject request = (JSONObject)configElementJSONObject.get("request");
		JSONObject queryParameters = (JSONObject)request.get("query_parameters");
		JSONObject header = (JSONObject)request.get("header");
		JSONObject pathParameters = (JSONObject)request.get("path_parameters");
		String method = (String)request.get("method");
		String rawUrl = (String)request.get("url");
		String url = bindPathParameters(rawUrl, pathParameters) + getStringQueryParameters(queryParameters);

		try {
			return new GithubRestApiDto(new URL(url), header, method);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
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

	private String bindPathParameters(String rawUrl, JSONObject pathParameters) {
		rawUrl = rawUrl.toLowerCase();
		Map<String, String> pathParameterMap = (Map)pathParameters.toMap();
		StringSubstitutor strSubstitutor = new StringSubstitutor(pathParameterMap, "{", "}");
		return strSubstitutor.replace(rawUrl);
	}

}
