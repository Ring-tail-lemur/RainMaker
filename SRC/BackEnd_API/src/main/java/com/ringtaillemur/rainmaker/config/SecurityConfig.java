package com.ringtaillemur.rainmaker.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        //http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();/

        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**", "/login/**", "/login/oauth2/code/github", "/api/cycletime", "/dorametric/**", "/test/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
                ).oauth2Login().redirectionEndpoint().baseUri("/oauth2/auth/code/github");
        return http.build();
    }
}
