package org.example.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.example.functions.api_service.github.GithubRestApiDto;
import org.example.functions.api_service.github.GithubRestApiGenerator;
import org.example.functions.api_service.github.GithubRestApiSender;
import org.example.functions.db_service.QueryGenerator;
import org.example.functions.db_service.QueryRunner;
import org.example.functions.util.ConfigReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpTriggerFunction {

  //deployment test2
  ConfigReader configReader = new ConfigReader();
  GithubRestApiSender githubRestApiSender = new GithubRestApiSender();
  QueryRunner queryRunner = QueryRunner.getQueryRunner();

  @FunctionName("HttpExample")
  public HttpResponseMessage run(
    @HttpTrigger(
      name = "req",
      methods = { HttpMethod.GET, HttpMethod.POST },
      authLevel = AuthorizationLevel.ANONYMOUS
    ) HttpRequestMessage<Optional<String>> request,
    final ExecutionContext context
  )
    throws IOException {
    JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();
    GithubRestApiGenerator githubRestApiGenerator = new GithubRestApiGenerator(
      request.getQueryParameters()
    );
    List<GithubRestApiDto> githubRestApiDtoList = githubRestApiGenerator.getGithubRestApiDtoList(
      jsonObjectConfig
    );
    Map<String, JSONArray> responseJSONArrayList = githubRestApiSender.sendAllGithubRestApi(
      githubRestApiDtoList
    );
    QueryGenerator queryGenerator = new QueryGenerator(jsonObjectConfig);
    List<String> queryList = queryGenerator.generateQueryList(
      responseJSONArrayList
    );
    queryRunner.runInsertQueries(queryList);
    return request
      .createResponseBuilder(HttpStatus.OK)
      .body(responseJSONArrayList.toString())
      .build();
  }
}
