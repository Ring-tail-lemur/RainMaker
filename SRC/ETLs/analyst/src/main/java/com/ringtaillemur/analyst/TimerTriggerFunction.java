package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.ringtaillemur.analyst.analysislogic.dorametric.LeadTimeForChange;
import java.io.IOException;

// test6
public class TimerTriggerFunction {

  LeadTimeForChange leadTimeForChange = LeadTimeForChange.getLeadTimeForChange();

  @FunctionName("TimerTrigger-Java")
  public void run(
    @TimerTrigger(
      name = "timerInfo",
      schedule = "0 */10 * * * *"
    ) String timerInfo,
    final ExecutionContext context
  )
    throws IOException {
    leadTimeForChange.calculateLeadTimeForChange();
  }
}
