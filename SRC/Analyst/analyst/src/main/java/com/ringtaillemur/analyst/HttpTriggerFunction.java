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
      try{
        TimerTriggerFunction timerTriggerFunction = new TimerTriggerFunction();
        timerTriggerFunction.run("HistoryCollector에 의해 호출", context);
        return request.createResponseBuilder(HttpStatus.OK).body("happy!!").build();
      }catch (Exception e){
         LogModule logModule = LogModule.getLogModule();
         logModule.sendLog(e, "LogModule HttpTrigger 실패");
      }
      return null;
  }
}
