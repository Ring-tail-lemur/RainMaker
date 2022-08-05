package com.ringtaillemur.analyst;

import java.io.IOException;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.DoraMetric;

public class TimerTriggerFunction {


	DoraMetric doraMetric = DoraMetric.getDoraMetric();

	@FunctionName("TimerTrigger-Java")
	public void run(
		@TimerTrigger(name = "timerInfo", schedule = "1-59/5 * * * * *") String timerInfo,
		final ExecutionContext context
	) throws IOException {
		doraMetric.calculateLeadTimeForChange();
	}
}
