package com.ringtaillemur.rainmaker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ringtaillemur.rainmaker.config.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// @Autowired
	// private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private SessionFilterInternal sessionFilterInternal;
	@Autowired
	private CustomAccessDeniedHandler customAccessDiniedHandler;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors() //(1)
			.and()
			.csrf() //(2)
			.disable()
			.addFilterBefore(sessionFilterInternal, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests(a -> a
				.antMatchers("/getPersonalAccessToken").hasAnyAuthority("FIRST_AUTH_USER","AUTH_NOT_REPOSITORY_SELECT", "AUTHED_HISTORY_COLLECT_NOT_ENDED_USER", "AUTHED_HISTORY_COLLECT_ENDED_USER")
					.antMatchers("/profile/**","/RepositorySelect/**").hasAnyAuthority("AUTH_NOT_REPOSITORY_SELECT", "AUTHED_HISTORY_COLLECT_NOT_ENDED_USER", "AUTHED_HISTORY_COLLECT_ENDED_USER")
					.anyRequest().hasAnyAuthority("AUTHED_HISTORY_COLLECT_ENDED_USER")
				)
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler(customAccessDiniedHandler)
			).oauth2Login().redirectionEndpoint().baseUri("/oauth2/auth/code/github");
		return http.build();
	}
	 protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
			.sessionFixation().none();
	 }
	 protected void configure2(HttpSecurity http) throws Exception {
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
	 }
}
