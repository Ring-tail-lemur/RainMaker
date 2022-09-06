package com.ringtaillemur.rainmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String FrontBaseUrl = "http://localhost:3000";


    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("https://api.github.com")
                .build();
    }
}
