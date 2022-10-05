package org.example.functions.collector.github.request_query_generator.graphql;

import java.net.MalformedURLException;

import org.example.functions.TestMockData;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class GithubGraphqlGeneratorTest {


	@Test
	void getHttpRequestDto() throws MalformedURLException {
		//given
		String configElementString = TestMockData.test1Element;
		JSONObject configElementJSONObject = new JSONObject(configElementString);

		//when
	}
}
