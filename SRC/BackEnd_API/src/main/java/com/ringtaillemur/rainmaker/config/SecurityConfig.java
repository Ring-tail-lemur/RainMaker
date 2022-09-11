package com.ringtaillemur.rainmaker.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        //http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();/

        http
                .cors() //(1)
                .and()
                .csrf() //(2)
                .disable()
                .sessionManagement() //(4)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**", "/login/**", "/login/oauth2/code/github", "/api/cycletime", "/dorametric/**").permitAll()
//                        .anyRequest().hasAuthority("FIRST_AUTH_USER")
                        .anyRequest().hasAuthority("FIRST_AUTH_USER")
//                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
                ).oauth2Login().redirectionEndpoint().baseUri("/oauth2/auth/code/github");
        return http.build();
    }


}
