package org.example.functions;

import java.util.Optional;

import org.example.functions.api_service.github.GithubRestApiSender;
import org.example.functions.util.ConfigReader;

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
		final ExecutionContext context) {
		String requestBody = request.getBody().orElseThrow(
			() -> new RuntimeException("There is no HttpRequest.Body"));
		return request.createResponseBuilder(HttpStatus.OK).body("Hello").build();
	}
}
