package org.example.functions.api_service.github;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.HttpStatusType;

class GithubApiRunnerTest {

	GithubApiRunner githubApiRunner = GithubApiRunner.getGithubApiRunner();
	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	@Test
	void runGithubApi() throws Exception {
		//given
		String jsonConfig = "{\n"
			+ "  \"commits\": {\n"
			+ "    \"request\": {\n"
			+ "      \"method\": \"GET\",\n"
			+ "      \"url\": \"https://api.github.com/repos/{OWNER}/{REPO}/commits\",\n"
			+ "      \"header\": {\n"
			+ "        \"accept\": \"application/vnd.github+json\"\n"
			+ "      },\n"
			+ "      \"path_parameters\": {\n"
			+ "      },\n"
			+ "      \"query_parameters\": {\n"
			+ "        \"per_page\": \"100\"\n"
			+ "      }\n"
			+ "    },\n"
			+ "    \"mapping\": {\n"
			+ "      \"sha\": [\"commit_id\", \"string\"],\n"
			+ "      \"author.id\": [\"author_id\", \"int\"],\n"
			+ "      \"commit.message\": [\"message\", \"string\"],\n"
			+ "      \"commit.author.date\": [\"commit_time\", \"datetime2\"]\n"
			+ "    }\n"
			+ "  },"
			+ "\"test1\": {"
			+ "	\"graphql\":{\n"
			+ "  repository(owner: \"{owner}\", name: \"{repo}\") {\n"
			+ "    pullRequest(number: 1) {\n"
			+ "      commits(first: 10) {\n"
			+ "        edges {\n"
			+ "          node {\n"
			+ "            commit {\n"
			+ "              oid\n"
			+ "              message\n"
			+ "            }\n"
			+ "          }\n"
			+ "        }\n"
			+ "      }\n"
			+ "      comments(first: 10) {\n"
			+ "        edges {\n"
			+ "          node {\n"
			+ "            body\n"
			+ "            author {\n"
			+ "              login\n"
			+ "            }\n"
			+ "          }\n"
			+ "        }\n"
			+ "      }\n"
			+ "      reviews(first: 10) {\n"
			+ "        edges {\n"
			+ "          node {\n"
			+ "            state\n"
			+ "          }\n"
			+ "        }\n"
			+ "      }\n"
			+ "    }\n"
			+ "  }\n"
			+ "}"
			+ "}"
			+ "}";

		JSONObject jsonObject = typeConverter.convertStringToJSONObject(jsonConfig);

		//when
		Map<String, JSONArray> stringJSONArrayMap = githubApiRunner.runGithubApi(jsonObject,
			new HttpRequestMessage<Optional<String>>() {
				@Override
				public URI getUri() {
					return null;
				}

				@Override
				public HttpMethod getHttpMethod() {
					return null;
				}

				@Override
				public Map<String, String> getHeaders() {
					return null;
				}

				@Override
				public Map<String, String> getQueryParameters() {
					HashMap<String, String> stringStringHashMap = new HashMap<>();
					stringStringHashMap.put("token", "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");
					stringStringHashMap.put("repo", "RainMaker");
					stringStringHashMap.put("owner", "Ring-tail-lemur");
					return stringStringHashMap;
				}

				@Override
				public Optional<String> getBody() {
					return Optional.empty();
				}

				@Override
				public HttpResponseMessage.Builder createResponseBuilder(HttpStatus httpStatus) {
					return null;
				}

				@Override
				public HttpResponseMessage.Builder createResponseBuilder(HttpStatusType httpStatusType) {
					return null;
				}
			});

		//then
		System.out.println("stringJSONArrayMap = " + stringJSONArrayMap);
	}
}
