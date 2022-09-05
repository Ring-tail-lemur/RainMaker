package org.example.functions.util;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class ConfigReaderTest {
	ConfigReader configReader = new ConfigReader();

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
	}
}
