package com.ringtaillemur.rainmaker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	//localTest용 세팅
	public static String frontEndBaseUrl;
	public static String backEndBaseUrl;
	//    public static final String clientId = "8189c16057d124b9324e";
	//    public static final String clientSecret = "e5231059eb31aa50d69a6a2154708a8a3f88954d";

	//Public용 세팅(안 건들여도 알아서 바꿔서 올라가니까 신경쓰지 말아줘요~!)
//	public static final String frontEndBaseUrl = "https://victorious-forest-095d4a310.1.azurestaticapps.net";
//	public static final String backEndBaseUrl = "https://spring-api-server.azurewebsites.net";

	public static String clientId;
	public static String clientSecret;

	@Bean
	public WebClient webClient() {
		return WebClient
			.builder()
			.baseUrl("https://api.github.com")
			.build();
	}
}
