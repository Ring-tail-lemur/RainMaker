package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.*;
import com.ringtaillemur.analyst.restapi.LogModule;

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
    LogModule logModule = LogModule.getLogModule();
    logModule.sendLog(new Exception("실행 테스트"), "로그 모듈 테스트 중입니다. 제발 됐으면 좋겠어요. ETL!");
    System.out.println("start");
    updateCommitsReleaseId.calculateUpdateCommitsReleaseId();
    pullRequestDirection.MakePullRequestDirection();
    leadTimeForChange.calculateLeadTimeForChange();
    changeFailureRate.calculateChangeFailureRate();
    timeToRestoreService.calculateTimeToRestoreService();
    System.out.println("end");
  }
}
