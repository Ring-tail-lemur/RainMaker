package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.*;

// test3
public class TimerTriggerFunction {

  //test 2
  private final LeadTimeForChange leadTimeForChange = LeadTimeForChange.getLeadTimeForChange();
  private final ChangeFailureRate changeFailureRate = ChangeFailureRate.getChangeFailureRate();
  private final TimeToRestoreService timeToRestoreService = TimeToRestoreService.getTimeToRestoreService();
  private final UpdateCommitsReleaseId updateCommitsReleaseId = UpdateCommitsReleaseId.getUpdateCommitsReleaseId();
  private final PullRequestDirection pullRequestDirection = PullRequestDirection.getPullRequestDirection();

  @FunctionName("TimerTrigger-Java")
  public void run(
    @TimerTrigger(
      name = "timerInfo",
      schedule = "0 */3 * * * *"
    ) String timerInfo,
    final ExecutionContext context
  )
    throws Exception {
    System.out.println("start");
    updateCommitsReleaseId.calculateUpdateCommitsReleaseId();
    pullRequestDirection.MakePullRequestDirection();
    leadTimeForChange.calculateLeadTimeForChange();
    changeFailureRate.calculateChangeFailureRate();
    timeToRestoreService.calculateTimeToRestoreService();
    System.out.println("end");
  }
}
