package com.ringtaillemur.rainmaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final SessionFilterInternal sessionFilterInternal;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors() //(1)
			.and()
			.csrf() //(2)
			.disable()
			.addFilterBefore(sessionFilterInternal, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests(a -> a
				.antMatchers("/login/**").permitAll()
				.antMatchers("/token").hasAnyAuthority("FIRST_AUTH_USER","AUTH_NOT_REPOSITORY_SELECT", "AUTHED_HISTORY_COLLECT_NOT_ENDED_USER", "AUTHED_HISTORY_COLLECT_ENDED_USER")
				.antMatchers("/profile/**","/RepositorySelect").hasAnyAuthority("AUTH_NOT_REPOSITORY_SELECT", "AUTHED_HISTORY_COLLECT_NOT_ENDED_USER", "AUTHED_HISTORY_COLLECT_ENDED_USER")
				.antMatchers("/dorametric/**", "/user/repository-info").hasAnyAuthority(OauthUserLevel.AUTHED_HISTORY_COLLECT_ENDED_USER.toString())
					.antMatchers("/api/check").hasAnyAuthority(OauthUserLevel.AUTHED_HISTORY_COLLECT_ENDED_USER.toString())
					.anyRequest().authenticated()
				)
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler(customAccessDeniedHandler)
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
