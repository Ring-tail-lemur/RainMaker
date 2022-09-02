package org.example.functions;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.example.functions.api_service.github.GithubRestApiDto;
import org.example.functions.api_service.github.GithubRestApiGenerator;
import org.example.functions.api_service.github.GithubRestApiSender;
import org.example.functions.util.ConfigReader;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class HttpTriggerFunction {

	ConfigReader configReader = new ConfigReader();
	GithubRestApiSender githubRestApiSender = new GithubRestApiSender();

	@FunctionName("HttpExample")
	public HttpResponseMessage run(
		@HttpTrigger(
			name = "req",
			methods = {HttpMethod.GET, HttpMethod.POST},
			authLevel = AuthorizationLevel.ANONYMOUS)
		HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) throws IOException {
		JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();
		GithubRestApiGenerator githubRestApiGenerator = new GithubRestApiGenerator(request.getQueryParameters());
		List<GithubRestApiDto> githubRestApiDtoList = githubRestApiGenerator.getGithubRestApiDtoList(jsonObjectConfig);
		List<JSONArray> responseJSONArrayList = githubRestApiSender.sendAllGithubRestApi(githubRestApiDtoList);
		return request.createResponseBuilder(HttpStatus.OK).body(responseJSONArrayList.toString()).build();
	}
}
