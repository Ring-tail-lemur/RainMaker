package com.ringtaillemur.rainmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //localTest용 세팅
//    public static final String frontEndBaseUrl= "http://127.0.0.1:3000";
//    public static final String backEndBaseUrl= "http://127.0.0.1:8080";
//    public static final String clientId = "8189c16057d124b9324e";
//    public static final String clientSecret = "e5231059eb31aa50d69a6a2154708a8a3f88954d";


    //Public용 세팅
    public static final String frontEndBaseUrl = "https://victorious-forest-095d4a310.1.azurestaticapps.net";
    public static final String backEndBaseUrl = "https://spring-api-server.azurewebsites.net";
    public static final String clientId = "42286a47489496b3129b";
    public static final String clientSecret = "effa9cd720c3db113f3ce8f73a0d13ffd3a0633f";
    @Bean
    public WebClient webClient() {
        return WebClient
            .builder()
            .baseUrl("https://api.github.com")
            .build();
    }
}
