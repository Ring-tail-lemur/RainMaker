package com.ringtaillemur.rainmaker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DeployProperty {

	@Value("${spring.dev.frontendUri}")
	public String frontEndBaseUri;
	@Value("${spring.dev.backendUri}")
	public String backEndBaeUri;
	@Value("${spring.security.oauth2.client.registration.github.clientId}")
	public String clientId;
	@Value("${spring.security.oauth2.client.registration.github.clientSecret}")
	public String clientSecret;

}
