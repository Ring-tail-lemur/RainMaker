package com.ringtaillemur.rainmaker.service;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.repository.GitUserRepository;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserConfigService {

    private final WebClient webClient;
    private final OAuthRepository oAuthRepository;

    public String getUserToken(String userId) {

        oAuthRepository.findById(Long.valueOf(userId));
        return null;
//        return token;
    }


    /**
     * 토큰을 넣어주면 유저의 모든 Repository 정보를 뺴내오는 Method
     * */
    public ArrayList<UserRepositoryDto> getUserRepositoryDtoByToken(String token) {

        JSONArray OrganizationArray = getOrganizationListByGithubApi(token);
        ArrayList<UserRepositoryDto> repositoryList = new ArrayList<>();

        for(var organization : OrganizationArray){
            String organizationName = ((JSONObject) organization).getString("login");
            ArrayList<UserRepositoryDto> repositoryListByGithubApi = getRepositoryListByGithubApi(organizationName, token);
            repositoryList.addAll(repositoryListByGithubApi);
        }

        return repositoryList;
    }

    public JSONArray getOrganizationListByGithubApi(String token) {

        try {
            URL url = new URL("https://api.github.com/user/orgs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
            conn.setRequestProperty("Authorization", "Bearer " + token); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            return new JSONArray(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public ArrayList<UserRepositoryDto> getRepositoryListByGithubApi(String organizationName, String token) {

        try {
            URL url = new URL(String.format("https://api.github.com/orgs/%s/repos", organizationName));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "application/vnd.github+json"); // header Content-Type 정보
            conn.setRequestProperty("Authorization", "Bearer " + token); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            ArrayList<UserRepositoryDto> userRepositoryDtoArrayList = new ArrayList<>();

            for(var eachRepository : jsonArray) {
                int id = (int) ((JSONObject) eachRepository).get("id");
                String repository = (String) ((JSONObject) eachRepository).get("name");
                LocalDateTime pushed_at = changeStringToDateTime( (String) ((JSONObject) eachRepository).get("pushed_at") );
                userRepositoryDtoArrayList.add(new UserRepositoryDto(id, organizationName, repository, pushed_at));
            }
            return userRepositoryDtoArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<UserRepositoryDto>();
        }
    }

    public LocalDateTime changeStringToDateTime(String pushedAtStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(pushedAtStr, formatter);
        return dateTime;
    }

    public String setUserWebhookByRepoName(String token, String owner_name, String repo_name) {
        try {
            Map<String, String> bodyMap = new HashMap();
            String body = "{\"config\": { \"url\": \"https://webhook.site/02ee4e5e-7543-464e-9e80-ab50386ac65e\", \"content_type\":\"'json'\", \"insecure_ssl\": \"'0'\" }, \"events\": [\"pull_request\", \"push\", \"label\", \"repository\", \"release\", \"issues\", \"create\", \"delete\", \"issue_comment\", \"pull_request_review_comment\"], \"active\": true}";
            BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> inserter = BodyInserters.fromValue(bodyMap);

            var responseSpec= webClient.post()
                    .uri(String.format("/repos/%s/%s/hooks", owner_name, repo_name))
//                    .accept(MediaType.APPLICATION_JSON)
//                    .header("application", "vnd.github+json")
                    .header("Authorization", "Bearer " + token)
                    .body(BodyInserters.fromValue(body))
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                    .block();

            return responseSpec;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
