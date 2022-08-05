package com.ringtaillemur.analyst;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Timer trigger.
 */
public class TimerTriggerFunction {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimerTrigger-Java")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "1-59 * * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        System.out.println(timerInfo);
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
        System.out.println("hello");
    }
}
