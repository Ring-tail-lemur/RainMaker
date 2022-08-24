package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.ChangeFailureRate;
import com.ringtaillemur.analyst.analysislogic.dorametric.LeadTimeForChange;
import com.ringtaillemur.analyst.analysislogic.dorametric.TimeToRestoreService;
import com.ringtaillemur.analyst.analysislogic.dorametric.UpdateCommitsReleaseId;
import com.ringtaillemur.analyst.dto.ReleaseDto;
import com.ringtaillemur.analyst.restapi.GetCommitsCompare;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

// test what?
public class TimerTriggerFunction {

  LeadTimeForChange leadTimeForChange = LeadTimeForChange.getLeadTimeForChange();
  ChangeFailureRate changeFailureRate = ChangeFailureRate.getChangeFailureRate();
  TimeToRestoreService timeToRestoreService = TimeToRestoreService.getTimeToRestoreService();
  UpdateCommitsReleaseId updateCommitsReleaseId = UpdateCommitsReleaseId.getUpdateCommitsReleaseId();

  @FunctionName("TimerTrigger-Java")
  public void run(
    @TimerTrigger(
      name = "timerInfo",
      schedule = "1-50 * * * * *"
    ) String timerInfo,
    final ExecutionContext context
  )
    throws Exception {
    updateCommitsReleaseId.calculateUpdateCommitsReleaseId();
    leadTimeForChange.calculateLeadTimeForChange();
    changeFailureRate.calculateChangeFailureRate();
    timeToRestoreService.calculateTimeToRestoreService();
  }
}
