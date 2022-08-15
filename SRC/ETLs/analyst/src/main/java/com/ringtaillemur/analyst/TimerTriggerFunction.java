package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.ChangeFailureRate;
import com.ringtaillemur.analyst.analysislogic.dorametric.LeadTimeForChange;
import com.ringtaillemur.analyst.analysislogic.dorametric.TimeToRestoreService;

// test8
public class TimerTriggerFunction {

	LeadTimeForChange leadTimeForChange = LeadTimeForChange.getLeadTimeForChange();
	ChangeFailureRate changeFailureRate = ChangeFailureRate.getChangeFailureRate();
	TimeToRestoreService timeToRestoreService = TimeToRestoreService.getTimeToRestoreService();

	@FunctionName("TimerTrigger-Java")
	public void run(
		@TimerTrigger(name = "timerInfo", schedule = "0 */10 * * * *") String timerInfo,
		final ExecutionContext context) {
		leadTimeForChange.calculateLeadTimeForChange();
		changeFailureRate.calculateChangeFailureRate();
		timeToRestoreService.calculateTimeToRestoreService();
	}
}
