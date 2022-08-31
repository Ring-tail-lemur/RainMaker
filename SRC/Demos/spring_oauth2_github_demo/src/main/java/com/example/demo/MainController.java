package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class MainController{
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/login/**")
    public void test(){
        System.out.println("hihi");
    }
    @GetMapping("/callback")
    public void testCallBack(){
        System.out.println("dddd");
    }

}
