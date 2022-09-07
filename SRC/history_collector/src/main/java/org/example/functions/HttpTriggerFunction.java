package org.example.functions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.api_service.github.GithubApiRunner;
import org.example.functions.api_service.github.HttpRequestSender;
import org.example.functions.db_service.QueryGenerator;
import org.example.functions.db_service.QueryRunner;
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
	HttpRequestSender httpRequestSender = new HttpRequestSender();
	QueryRunner queryRunner = QueryRunner.getQueryRunner();
	GithubApiRunner githubApiRunner = GithubApiRunner.getGithubApiRunner();
	@FunctionName("HttpExample")
	public HttpResponseMessage run(
		@HttpTrigger(
			name = "req",
			methods = {HttpMethod.GET, HttpMethod.POST},
			authLevel = AuthorizationLevel.ANONYMOUS)
		HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) throws Exception {
		JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();
		Map<String, JSONArray> responseJSONArrayList = githubApiRunner.runGithubApi(jsonObjectConfig, request);

		QueryGenerator queryGenerator = new QueryGenerator(jsonObjectConfig);
		List<String> queryList = queryGenerator.generateQueryList(responseJSONArrayList);
		queryRunner.runInsertQueries(queryList);
		return request.createResponseBuilder(HttpStatus.OK).body(responseJSONArrayList.toString()).build();
	}
}
