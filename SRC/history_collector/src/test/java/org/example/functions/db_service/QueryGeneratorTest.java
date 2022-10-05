package org.example.functions.db_service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.example.functions.util.FileReader;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class QueryGeneratorTest {

	private final FileReader fileReader = FileReader.getInstance();
	private final TypeConverter typeConverter = TypeConverter.getTypeConverter();

	@Test
	void generateQueryList() {

		// JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();
		// Map<String, JSONArray> responseJSONArrayList = githubRestApiSender.sendAllGithubRestApi(githubRestApiDtoList);
		// QueryGenerator queryGenerator = new QueryGenerator(responseJSONArrayList);
	}

	@Test
	void 재귀함수제발() {
		JSONArray objects = new JSONArray();
		JSONArray test = typeConverter.normalizeJsonObject(
			objects.put(
				typeConverter.convertStringToJSONObject(
					fileReader.readFile(
						"/static/json/mock_graphql_result.json"
					)
				)
			)
		);
		List<Integer> collect = new ArrayList<>();
		for (Object o : test) {
			collect.add(
				((JSONObject)o).getJSONObject("data")
					.getJSONObject("repository")
					.getJSONObject("pullRequests")
					.getJSONObject("nodes")
					.getJSONObject("comments")
					.getJSONObject("nodes")
					.getInt("databaseId")
			);
		}
		Assertions.assertThat(collect.stream().count()).isEqualTo(collect.stream().distinct().count());
	}

}
