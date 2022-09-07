package org.example.functions.api_service.github;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.api_service.github.request_query_generator.ApiGeneratorFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.functions.HttpRequestMessage;

public class GithubApiRunner {

	private static GithubApiRunner githubApiRunner = new GithubApiRunner();
	private HttpRequestSender httpRequestSender = new HttpRequestSender();
	private ApiGeneratorFactory apiGeneratorFactory;

	private GithubApiRunner() {
	}

	public static GithubApiRunner getGithubApiRunner() {
		return githubApiRunner;
	}

	public Map<String, JSONArray> runGithubApi(JSONObject jsonObjectConfig,
		HttpRequestMessage<Optional<String>> request) throws Exception {
		apiGeneratorFactory = new ApiGeneratorFactory(request.getQueryParameters());
		List<HttpRequestDto> httpRequestDtoList = apiGeneratorFactory.getHttpRequestDtoList(jsonObjectConfig);
		return httpRequestSender.sendAllHttpRequest(httpRequestDtoList);
	}

}
