package com.ringtaillemur.analyst;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.microsoft.azure.functions.ExecutionContext;

class TimerTriggerFunctionTest {

	private final TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();

	@Test
	void run() throws Exception {
		timerTriggerFunction.run(null, null);
	}
}
