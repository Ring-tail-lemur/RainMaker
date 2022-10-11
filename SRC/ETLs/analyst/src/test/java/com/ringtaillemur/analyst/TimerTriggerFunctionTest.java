package com.ringtaillemur.analyst;

import java.io.IOException;
import java.util.logging.Logger;

import com.ringtaillemur.analyst.analysislogic.dorametric.PullRequestDirection;
import org.junit.jupiter.api.Test;

import com.microsoft.azure.functions.ExecutionContext;

class TimerTriggerFunctionTest {

	private final TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();
	private final PullRequestDirection pullRequestDirection = PullRequestDirection.getPullRequestDirection();
	@Test
	void run() throws Exception {
		timerTriggerFunction.run(null, null);
	}

	@Test
	void hi() throws IOException {
		pullRequestDirection.MakePullRequestDirection();
	}
}
