package com.ringtaillemur.rainmaker.service;


import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserConfigService {

    private final WebClient webClient;
    private final OAuthRepository oAuthRepository;
    private final RepositoryRepository repositoryRepository;

    /**
    * 현재 들어온 유저의 Remote_ID를 리턴하는 함수
    * @return userId
    * */
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong((String)authentication.getPrincipal());
    }

    /**
     * 현재 들어온 유저의 Remote_ID를 리턴하는 함수
     * @param userId Long, getUserId() 함수를 통해 얻은 userId
     * @return Token - String
     * */
    public String getToken(Long userId) {
        Optional<OAuthUser> user = oAuthRepository.findByUserRemoteId(userId);
        return user.get().getOauthToken();
    }


    /**
     * repo 관련 데이터 배열을 넣어주는 경우
     * @1. webhook 등록
     * @2. Azure Function 호출
     * @3. DB Repo 테이블에 등록.
     * */
    public void registerRepository(RegisterRepoIdDto repoIds) {
        List<String> repoIdsList = repoIds.getRepoIds();
        String token = getToken(getUserId());
        List<Repository> repositories = new ArrayList<>();
        for(String repo : repoIdsList) {
            String[] strings = repo.split(",");
            String repo_id = strings[0];
            String owner_name = strings[1];
            String repo_name = strings[2];
            repositories.add(getRepositoryInfoByGithubApi(owner_name, repo_name, token));
            setUserWebhookByRepoName(token, owner_name, repo_name);
            triggerHistoryCollector(owner_name, repo_name, token);
        }
        repositoryRepository.saveAll(repositories);
    }

    /**
     * 토큰을 넣어주면 유저의 모든 Repository 정보를 뺴내오는 Method
     * */
    public ArrayList<UserRepositoryDto> getUserRepositoryDtoByToken(String token) {

        JSONArray OrganizationArray = getOrganizationListByGithubApi(token);
        ArrayList<UserRepositoryDto> repositoryList = new ArrayList<>();

        for(var organization : OrganizationArray){
            String organizationName = ((JSONObject) organization).getString("login");
            Long organization_id = Long.valueOf(((JSONObject) organization).getInt("id"));

            List<Repository> checkedRepository = repositoryRepository.findByOwnerOrganizationId(organization_id);
            List<Long> repositoryIds = new ArrayList<>();
            for(var repository : checkedRepository ){
                repositoryIds.add(repository.getId());
            }


            ArrayList<UserRepositoryDto> repositoryListByGithubApi = getRepositoryListByGithubApi(organizationName, token, repositoryIds);
            repositoryList.addAll(repositoryListByGithubApi);
        }

        return repositoryList;
    }

    public String triggerHistoryCollector(String organizationName, String repositoryName, String token) {

        try {
            WebClient ServerlessFunctionClient = WebClient.builder()
                    .baseUrl("https://github-history-collector.azurewebsites.net")
                    .build();
            String block = ServerlessFunctionClient.get()
                    .uri(String.format("/api/HttpExample?owner_name=%s&repository_name=%s&token=%s", organizationName, repositoryName, token))
                    .retrieve()
                    .bodyToMono(String.class)
                    .toString();
            // todo : 추후 이쪽 부분 변경이 필요함. 여기서 비동기로 실행이 완료되었다면, 권한을 바꿔주는 식의 로직이 필요함.

            return block;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
                userRepositoryDtoArrayList.add(new UserRepositoryDto(id, organizationName, repository, pushed_at, false));
            }
            return userRepositoryDtoArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<UserRepositoryDto>();
        }
    }

    public ArrayList<UserRepositoryDto> getRepositoryListByGithubApi(String organizationName, String token, List<Long> repositoryIds) {

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
                if(repositoryIds.contains(Long.valueOf(id))){
                    userRepositoryDtoArrayList.add(new UserRepositoryDto(id, organizationName, repository, pushed_at, true));
                } else {
                    userRepositoryDtoArrayList.add(new UserRepositoryDto(id, organizationName, repository, pushed_at, false));
                }
            }
            return userRepositoryDtoArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<UserRepositoryDto>();
        }
    }

    public Repository getRepositoryInfoByGithubApi(String organizationName, String repositoryName, String token) {

        try {
            URL url = new URL(String.format("https://api.github.com/repos/%s/%s", organizationName, repositoryName));
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

            JSONObject eachRepository = new JSONObject(sb.toString());

            Long repository_id = Long.valueOf((int) eachRepository.get("id"));
            JSONObject owner = (JSONObject) eachRepository.get("owner");
            Long owner_organization_id = Long.valueOf((int) owner.get("id"));
            Repository repository = Repository.builder()
                    .id(repository_id)
                    .name(repositoryName)
                    .ownerType(OwnerType.ORGANIZATION)
                    .ownerOrganizationId(owner_organization_id)
                    .build();

            return repository;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            String body = "{\"config\": { \"url\": \"https://github-listener-nodejs.azurewebsites.net\", \"content_type\":\"'json'\", \"insecure_ssl\": \"'0'\" }, \"events\": [\"pull_request\", \"push\", \"label\", \"repository\", \"release\", \"issues\", \"create\", \"delete\", \"issue_comment\", \"pull_request_review_comment\"], \"active\": true}";
            BodyInserter<Map<String, String>, ReactiveHttpOutputMessage> inserter = BodyInserters.fromValue(bodyMap);

            String responseSpec = webClient.post()
                    .uri(String.format("/repos/%s/%s/hooks", owner_name, repo_name))
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
