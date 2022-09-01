package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collections;
import java.util.Map;

@RestController
public class MainController{

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
//    @GetMapping("/login/**")
//    public void test(){
//        System.out.println("hihi");
//    }
    @GetMapping("/callback")
    public void testCallBack(){
        System.out.println("dddd");
    }

    @GetMapping("/login/code/github")
    public Map<String, Object> testMap(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

//    @GetMapping("/login/auth2/code/github")
//    public Map<String, Object> testMap2(@AuthenticationPrincipal OAuth2User principal) {
//        return Collections.singletonMap("name", principal.getAttribute("name"));
//    }

    @GetMapping("/login/oauth2/code/github")
    public void testMap2(@RequestParam(value = "code", required = false, defaultValue = "test")String code,
                         @RequestParam(value = "state", required = false, defaultValue = "test")String state) {
        System.out.println("code : " + code);
        System.out.println("state : " + state);

    }

//    @GetMapping
//    public void authMain(
//            @RequestParam String code, @RequestParam String state
//    ){
//        System.out.println("code : " + code);
//        System.out.println("\nstate : " + state);
//    }
    @GetMapping("/auth_index")
    public void authIndexMain() {
        System.out.println("here, auth index");
    }
}
