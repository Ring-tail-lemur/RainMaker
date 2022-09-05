package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.service.oauth2.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@RestController
public class OAuthContoller {
    @Autowired
    private HttpSession session;
    @Autowired
    CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    OAuthRepository oAuthRepository;
    @GetMapping("/login/oauth2/code/github")
    public RedirectView testMap2(@RequestParam(value = "code", required = false, defaultValue = "test")String code,
                           @RequestParam(value = "state", required = false, defaultValue = "test")String state) throws IOException {
        String clientId = "8189c16057d124b9324e";
        String clientSecret = "e5231059eb31aa50d69a6a2154708a8a3f88954d";
        System.out.println("code : " + code);
        System.out.println("state : " + state);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("code", code);
        params.add("client_secret", clientSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept","application/json");
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://github.com/login/oauth/access_token", HttpMethod.POST, entity,String.class);
        /*
        access_token=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
        scope=admin%3Aorg%2Cadmin%3Apublic_key%2Cadmin%3Arepo_hook%2Crepo
        token_type=bearer
        이렇게 온다.
         */
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        jsonNode = mapper.readTree(response.getBody());
        String userAccessToken = String.valueOf(jsonNode.findValue("access_token"));
        String refreshToken = String.valueOf(jsonNode.findValue("refresh_token"));
        System.out.println(userAccessToken);
        System.out.println("refresh_token : " + refreshToken);
        System.out.println("Bearer ".concat(userAccessToken.replace("\"","")));
        //여기까지가 Token 받아오는 부분



        URL url = new URL("https://api.github.com/user");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Authorization", "Bearer ".concat(userAccessToken.replace("\"","")));
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        BufferedReader bufferedReader = null;
        String inputLine;
        if(http.getResponseCode() > 199 && http.getResponseCode()<300){
            bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        }else{
            bufferedReader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }
        inputLine = bufferedReader.readLine();
        http.disconnect();
        OAuthUser inputOAuthUser = stringToJson(inputLine, userAccessToken);

//
//        /* id, name, token, url, email 넣은 OAuthUser 생성*/
//        session.setAttribute("OAUTH_USER", inputOAuthUser);

        session.setAttribute("OAUTH_USER", inputOAuthUser);
        if(session.getId() == null){
            System.out.println("????");
        }else{
            System.out.println(session.getId());
            System.out.println(session.getAttribute("OAUTH_USER"));
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:3000");
        return redirectView;
    }

    public <T> OAuthUser stringToJson(String inputString, String OauthToken){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        JsonUser user = gson.fromJson(inputString, JsonUser.class);
        user.oauth_token = OauthToken;
        System.out.println("user.oauth_token : "+user.oauth_token);


        OAuthUser oAuthUser  = new OAuthUser(user.id, user.login, user.url, user.oauth_token);
        try{
            Optional<OAuthUser> tmpUser = oAuthRepository.findByUserRemoteId(oAuthUser.getUserRemoteId());
            System.out.println("pres! : "+tmpUser.get().toString());
            tmpUser.get().update(oAuthUser.getOauthToken());
            oAuthRepository.save(tmpUser.get());
        }catch (Exception e){
            oAuthRepository.save(oAuthUser);
        }

        return oAuthUser;
    }


    private class JsonUser {
        String oauth_token;
        Long id;
        String url;
        String login;
    }


    @GetMapping("/test/tool")
    public String testGet(){
        System.out.println("dd");
        return "test OK";
    }
}
