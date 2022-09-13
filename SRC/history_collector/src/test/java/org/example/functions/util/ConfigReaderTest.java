package org.example.functions.util;

import org.assertj.core.api.Assertions;
import org.example.functions.TestMockData;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class ConfigReaderTest {
	ConfigReader configReader = ConfigReader.getConfigReader();

	@Test
	void getJsonObjectConfig() {
		//given

		//when
		JSONObject jsonObjectConfig = configReader.getJsonObjectConfig();

		//then
		System.out.println("jsonObjectConfig = " + jsonObjectConfig);
	}

	@Test
	void getStringConfig() {
		//given
		String filePath = "/static/json/graphql/test1.graphql";

		//when
		String stringConfig = configReader.getStringConfig(filePath)
			.replace("\n", "")
			.replace("\r", "")
			.replace("\t", "")
			.replace(" ", "");
		String test1Graphql = TestMockData.test1Graphql
			.replace("\n", "")
			.replace("\r", "")
			.replace("\t", "")
			.replace(" ", "");

		//then
		Assertions.assertThat(stringConfig).isEqualTo(test1Graphql);
	}
}
