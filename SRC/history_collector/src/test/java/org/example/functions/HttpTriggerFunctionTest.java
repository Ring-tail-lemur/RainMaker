package org.example.functions;

import java.util.HashMap;

import org.example.functions.mock.HttpRequestMessageMock;
import org.junit.jupiter.api.Test;

class HttpTriggerFunctionTest {

	private final HttpTriggerFunction httpTriggerFunction = new HttpTriggerFunction();

	HttpRequestMessageMock request = new HttpRequestMessageMock(){{
		setQueryParameters(new HashMap<>(){{
			put("owner_name", "Ring-tail-lemur");
			put("repository_name", "RainMaker");
			put("token", "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");
		}});
	}};


	@Test
	void run() throws Exception {
		httpTriggerFunction.run(request, null);
	}

}
