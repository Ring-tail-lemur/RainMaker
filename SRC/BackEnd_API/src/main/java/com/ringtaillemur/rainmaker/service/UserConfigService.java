package com.ringtaillemur.rainmaker.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Service
public class UserConfigService {

    public JSONArray parse(String token) {

        JSONArray OrganizationArray = getOrganizationListByGithubApi(token);

        for(var organization : OrganizationArray){
            String organizationName = ((JSONObject) organization).getString("login");
            getRepositoryListByGithubApi(organizationName);
        }

        return null;
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

    public static <T> ArrayList<T> getRepositoryListByGithubApi(String organizationName) {
        return null;
    }
}
