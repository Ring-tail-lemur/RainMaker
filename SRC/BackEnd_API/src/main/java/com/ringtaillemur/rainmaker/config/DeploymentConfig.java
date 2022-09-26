package com.ringtaillemur.rainmaker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring")
public class DeploymentConfig {
	private WebClientConfig webClientConfig;

	public WebClientConfig getWebClientConfig() {

		return webClientConfig;
	}
}

