package org.example.functions.collector.github;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.collector.github.request_query_generator.ApiGeneratorFactory;
import org.example.functions.dto.HttpRequestDto;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.functions.HttpRequestMessage;

public class GithubApiRunner {

	private static final GithubApiRunner githubApiRunner = new GithubApiRunner();
	private final HttpRequestSender httpRequestSender = new HttpRequestSender();

	private GithubApiRunner() {
	}

	public static GithubApiRunner getGithubApiRunner() {
		return githubApiRunner;
	}

	public Map<String, JSONArray> runGithubApi(JSONObject jsonObjectConfig,
		HttpRequestMessage<Optional<String>> request) throws Exception {
		ApiGeneratorFactory apiGeneratorFactory = new ApiGeneratorFactory(request.getQueryParameters());
		List<HttpRequestDto> httpRequestDtoList = apiGeneratorFactory.getAllHttpRequestDtoList(jsonObjectConfig);
		return httpRequestSender.sendAllHttpRequest(httpRequestDtoList);
	}
}
