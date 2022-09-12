package com.ringtaillemur.rainmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String frontEndBaseUrl= "http://127.0.0.1:3000";
    public static final String backEndBaseUrl= "http://127.0.0.1:8080";
    public static final String clientId = "8189c16057d124b9324e";
    public static final String clientSecret = "e5231059eb31aa50d69a6a2154708a8a3f88954d";
    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("https://api.github.com")
                .build();
    }
}
