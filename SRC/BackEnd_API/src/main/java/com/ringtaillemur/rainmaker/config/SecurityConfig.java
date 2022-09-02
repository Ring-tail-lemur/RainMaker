package com.ringtaillemur.rainmaker.config;


import com.ringtaillemur.rainmaker.service.oauth2.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        //http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();/
        OAuth2UserService<OAuth2UserRequest, OAuth2User> CustomOAuth2UserService = null;
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**", "/login/**", "/login/oauth2/code/github", "/api/cycletime", "/dorametric/**").permitAll()
                        .antMatchers().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .oauth2Login().userInfoEndpoint().userService(CustomOAuth2UserService);
//                ).oauth2Login().redirectionEndpoint().baseUri("/oauth2/auth/code/github");
        return http.build();
    }
}
