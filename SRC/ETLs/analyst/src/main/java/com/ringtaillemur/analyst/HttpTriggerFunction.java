package com.ringtaillemur.analyst;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.ringtaillemur.analyst.restapi.LogModule;
import java.util.Optional;

//deploy
public class HttpTriggerFunction {
  private LogModule logModule = LogModule.getLogModule();
  @FunctionName("AnalystHttpTrigger")
  public HttpResponseMessage run(

    @HttpTrigger(
      name = "req",
      methods = { HttpMethod.GET, HttpMethod.POST },
      authLevel = AuthorizationLevel.ANONYMOUS
    ) HttpRequestMessage<Optional<String>> request,
    final ExecutionContext context
  )
    throws Exception {
    TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();
    logModule.sendLog(new Exception("그냥 테스트용이야."), "이거 실행되는거 맞겠지? 그렇겠지? 제발 그랬으면 좋겠다.(HttpTrigger)");
    timerTriggerFunction.run("HistoryCollector에 의해 호출", context);
    return request.createResponseBuilder(HttpStatus.OK).body("happy!!").build();
  }
}
