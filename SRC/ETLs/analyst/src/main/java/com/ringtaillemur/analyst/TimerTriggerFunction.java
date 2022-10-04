package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.*;

// test3
public class TimerTriggerFunction {

  //test 2
  LeadTimeForChange leadTimeForChange = LeadTimeForChange.getLeadTimeForChange();
  ChangeFailureRate changeFailureRate = ChangeFailureRate.getChangeFailureRate();
  TimeToRestoreService timeToRestoreService = TimeToRestoreService.getTimeToRestoreService();
  UpdateCommitsReleaseId updateCommitsReleaseId = UpdateCommitsReleaseId.getUpdateCommitsReleaseId();
  PullRequestDirection pullRequestDirection = PullRequestDirection.getPullRequestDirection();

  @FunctionName("TimerTrigger-Java")
  public void run(
    @TimerTrigger(
      name = "timerInfo",
      schedule = "0 */3 * * * *"
    ) String timerInfo,
    final ExecutionContext context
  )
    throws Exception {
    updateCommitsReleaseId.calculateUpdateCommitsReleaseId();
    pullRequestDirection.MakePullRequestDirection();
    leadTimeForChange.calculateLeadTimeForChange();
    changeFailureRate.calculateChangeFailureRate();
    timeToRestoreService.calculateTimeToRestoreService();
  }
}
