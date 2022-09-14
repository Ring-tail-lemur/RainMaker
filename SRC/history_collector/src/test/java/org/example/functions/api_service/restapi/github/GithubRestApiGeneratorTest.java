package org.example.functions.api_service.restapi.github;

import static org.assertj.core.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.example.functions.api_service.github.HttpRequestDto;
import org.example.functions.api_service.github.request_query_generator.restapi.GithubRestApiGenerator;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class GithubRestApiGeneratorTest {

	GithubRestApiGenerator githubRestApiGenerator;
	JSONObject oneElementConfigJSONObject = new JSONObject(""
		+ "{  \"commits\": {\"request\": {\"method\": \"GET\",\"url\": \"https://api.github.com/repos/{OWNER}/{REPO}/commits\",\"header\": {\"User-Agent\": \"PostmanRuntime/7.29.2\"},\"path_parameters\": {},\"query_parameters\": {}},\"mapping\": {\"sha\": \"commit_id\",\"author.id\": \"author_id\",\"commit.message\": \"message\",\"commit.author.date\": \"commit_time\"}}}\n");
	;
	JSONObject multiElementConfigJSONObject = multiElementConfigJSONObject = new JSONObject(""
		+ "{ \"test1\": {\"request\": {\"method\": \"GET\",\"url\": \"https://test1/{OWNER}/{REPO}\",\"header\": {},\"path_parameters\": {},\"query_parameters\": {}}},"
		+ "  \"test2\": {\"request\": {\"method\": \"GET\",\"url\": \"https://test2/{OWNER}/{REPO}\",\"header\": {\"Accept\": \"*/*\",\"User-Agent\": \"PostmanRuntime/7.29.2\"},\"path_parameters\": {},\"query_parameters\": {\"time\": 321}}},"
		+ "  \"test3\": {\"request\": {\"method\": \"POST\",\"url\": \"https://test3/{OWNER}/{REPO}\",\"header\": {},\"path_parameters\": {},\"query_parameters\": {}}},\n"
		+ "  \"test4\": {\"request\": {\"method\": \"GET\",\"url\": \"https://test4/{OWNER}/{REPO}\",\"header\": {},\"path_parameters\": {},\"query_parameters\": {}}},\n"
		+ "	 \"test5\": {\"request\": {\"method\": \"UPDATE\",\"url\": \"https://test5/{OWNER}/{REPO}\",\"header\": {},\"path_parameters\": {},\"query_parameters\": {}}}}\n");
	;

	void DtoGenerateTest() throws MalformedURLException {
		//given
		githubRestApiGenerator =
			new GithubRestApiGenerator("test-for-fake-project", "Ring-tail-lemur", "token");


		//when
		HttpRequestDto httpRequestDtoList = githubRestApiGenerator.getHttpRequestDto(
			oneElementConfigJSONObject.getJSONObject("commits"), "commits");

		//then
		assertThat(httpRequestDtoList.getUrl()).isEqualTo(
			new URL("https://api.github.com/repos/Ring-tail-lemur/test-for-fake-project/commits"));
		assertThat(httpRequestDtoList.getMethod()).isEqualTo("GET");
		assertThat(httpRequestDtoList.getHeader().getString("User-Agent")).isEqualTo("PostmanRuntime/7.29.2");
		assertThat(httpRequestDtoList.getHeader().getString("token")).isEqualTo("token");
	}

	@Test
	void DtoListGenerateTestOneElement() throws MalformedURLException {
		//given
		githubRestApiGenerator =
			new GithubRestApiGenerator("test-for-fake-project", "Ring-tail-lemur", "token");

		//when
		List<HttpRequestDto> httpRequestDtoList = githubRestApiGenerator.getHttpRequestDtoList(
			oneElementConfigJSONObject);

		//then
		assertThat(httpRequestDtoList.size()).isEqualTo(1);
		assertThat(httpRequestDtoList.get(0).getUrl()).isEqualTo(
			new URL("https://api.github.com/repos/Ring-tail-lemur/test-for-fake-project/commits"));
		assertThat(httpRequestDtoList.get(0).getMethod()).isEqualTo("GET");
		assertThat(httpRequestDtoList.get(0).getHeader().getString("User-Agent")).isEqualTo("PostmanRuntime/7.29.2");
		assertThat(httpRequestDtoList.get(0).getHeader().getString("token")).isEqualTo("token");
	}

	@Test
	void DtoListGenerateTestMultiElement() throws MalformedURLException {
		//given
		githubRestApiGenerator =
			new GithubRestApiGenerator("test-for-fake-project", "Ring-tail-lemur", "token");

		//when
		List<HttpRequestDto> httpRequestDtoList = githubRestApiGenerator.getHttpRequestDtoList(
			multiElementConfigJSONObject);

		//then
		assertThat(httpRequestDtoList).hasSize(5);
	}

}
