package org.example.functions;

import java.util.HashMap;

import org.example.functions.mock.HttpRequestMessageMock;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

class HttpTriggerFunctionTest {

	private final HttpTriggerFunction httpTriggerFunction = new HttpTriggerFunction();

	HttpRequestMessageMock request = new HttpRequestMessageMock(){{
		setQueryParameters(new HashMap<>(){{
			put("owner_name", "Ring-tail-lemur");
			put("repository_name", "test-for-fake-project");
			put("token", "??????맟춰보세여");
		}});
	}};


	@Test
	void run() throws Exception {
		new JSONArray("[{\"hello\":\"okd\"},{\"hello\":\"okd\"}]");
	}

}
