package com.ringtaillemur.analyst;

import java.util.List;
import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

public class HttpTriggerFunction {
	@FunctionName("AnalystHttpTrigger")
	public HttpResponseMessage run(
		@HttpTrigger(name = "req",
			methods = {HttpMethod.GET, HttpMethod.POST},
			authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) throws Exception {
		TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();
		timerTriggerFunction.run("HistoryCollector에 의해 호출", context);
		return request
			.createResponseBuilder(HttpStatus.OK)
			.body("happy!!")
			.build();
	}
}
