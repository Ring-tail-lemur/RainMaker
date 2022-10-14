package org.example.functions;

import java.util.HashMap;

import org.example.functions.mock.HttpRequestMessageMock;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

class HttpTriggerFunctionTest {

	private final HttpTriggerFunction httpTriggerFunction = new HttpTriggerFunction();

	HttpRequestMessageMock request = new HttpRequestMessageMock(){{
		setQueryParameters(new HashMap<>(){{
			put("owner", "Ring-tail-lemur");
			put("repo", "RainMaker");
			put("token", "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");
		}});
	}};


	@Test
	void run() throws Exception {
		new JSONArray("[{\"hello\":\"okd\"},{\"hello\":\"okd\"}]");
	}

}
