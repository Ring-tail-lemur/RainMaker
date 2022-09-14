package org.example.functions.api_service.github.request_query_generator.graphql;

import static org.assertj.core.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.example.functions.TestMockData;
import org.example.functions.api_service.github.HttpRequestDto;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class GithubGraphqlGeneratorTest {

	GithubGraphqlGenerator githubGraphqlGenerator = new GithubGraphqlGenerator(Map.of(
		"owner", "Ring-tail-lemur",
		"repo", "RainMaker",
		"token", "test_token"
	));


	@Test
	void getHttpRequestDto() throws MalformedURLException {
		//given
		String configElementString = TestMockData.test1Element;
		JSONObject configElementJSONObject = new JSONObject(configElementString);

		//when
		HttpRequestDto graphqlHttpRequestDto = githubGraphqlGenerator.getHttpRequestDto(configElementJSONObject, "test1");

		//then
		String body = graphqlHttpRequestDto.getBody()
			.replace("\r", "")
			.replace("\t", "")
			.replace("\n", "")
			.replace(" ", "");
		String test1BindParameterGraphql = TestMockData.test1BindParameterGraphql
			.replace("\r", "")
			.replace("\t", "")
			.replace("\n", "")
			.replace(" ", "");

		assertThat(graphqlHttpRequestDto.getRequestType()).isEqualTo("test1");
		assertThat(graphqlHttpRequestDto.getUrl()).isEqualTo(new URL("https://api.github.com/graphql"));
		assertThat(graphqlHttpRequestDto.getMethod()).isEqualTo("POST");
		assertThat(graphqlHttpRequestDto.getHeader().get("token")).isEqualTo("test_token");
		assertThat(body).isEqualTo(test1BindParameterGraphql);
	}
}
