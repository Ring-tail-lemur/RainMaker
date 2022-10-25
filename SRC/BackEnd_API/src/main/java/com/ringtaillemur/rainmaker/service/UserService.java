package com.ringtaillemur.rainmaker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.websocket.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.configdto.DeployProperties;
import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollectorDto;
import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;
import com.ringtaillemur.rainmaker.dto.securitydto.SessionMemory;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepositoryResponseDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryInfoDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;
import com.ringtaillemur.rainmaker.util.exception.ConditionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final WebClient webClient;
	private final OAuthRepository oAuthRepository;
	private final DeployProperties deployProperties;
	private final SessionMemory sessionMemory;
	private final RepositoryService repositoryService;
	private final GitOrganizationService gitOrganizationService;
	private final OAuthUserRepositoryService oAuthUserRepositoryService;
	private final EntityManager entityManager;

	@Transactional
	public void setOAuthToken(String oAuthToken) throws Exception {
		if (!verifyToken(oAuthToken)) {
			throw new AuthenticationException("토큰이 유효하지 않습니다.");
		}
		OAuthUser currentUser = getCurrentUser();
		currentUser.setOauthToken(oAuthToken);
		changeOAuthLevel(currentUser);
	}

	private boolean verifyToken(String oAuthToken) throws IOException {
		URL url = new URL("https://api.github.com/user");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", String.format("Bearer %s", oAuthToken));
		return connection.getResponseCode() == 200;
	}

	private void changeOAuthLevel(OAuthUser currentUser) {
		if (currentUser.getUserLevel() == OauthUserLevel.FIRST_AUTH_USER) {
			currentUser.setUserLevel(OauthUserLevel.AUTH_NOT_REPOSITORY_SELECT);
		}
	}

	public OAuthUser getCurrentUser() throws Exception {
		long currentUserId = getCurrentUserId();
		return oAuthRepository.findById(currentUserId)
			.orElseThrow(() -> new Exception("로그인한 유저가 존재하지 않습니다."));
	}

	public List<UserRepositoryDto> getUserRepositoryDtoByToken(String token) throws Exception {

		JSONArray organizationArray = getOrganizationListByGithubApi(token);
		List<UserRepositoryDto> repositoryList = new ArrayList<>();

		organizationArray.toList().stream()
			.map(organization -> (JSONObject)organization)
			.map(organization -> organization.getString("login"));
		for (Object organization : organizationArray) {
			String organizationName = ((JSONObject)organization).getString("login");
			List<Long> repositoryIds = getCheckedRepoIds();
			List<UserRepositoryDto> repositoryListByGithubApi = getRepositoryListByGithubApi(organizationName,
				token, repositoryIds);
			repositoryList.addAll(repositoryListByGithubApi);
		}

		return repositoryList;
	}

	public JSONArray getOrganizationListByGithubApi(String token) {

		try {
			URL url = new URL("https://api.github.com/user/orgs");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

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

	public List<Long> getCheckedRepoIds() throws Exception {
		List<OAuthUserRepositoryTable> oAuthUserRepositories =
			oAuthUserRepositoryService.getOAuthUserRepositoryTableByOAuthUser(getCurrentUser());
		return oAuthUserRepositories.stream()
			.map(oAuthUserRepositoryTable -> oAuthUserRepositoryTable.getRepository().getId())
			.toList();
	}

	public List<UserRepositoryDto> getRepositoryListByGithubApi(String organizationName, String token,
		List<Long> repositoryIds) {

		try {
			URL url = new URL(String.format("https://api.github.com/orgs/%s/repos", organizationName));
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

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
			List<UserRepositoryDto> userRepositoryDtoArrayList = new ArrayList<>();

			for (var eachRepository : jsonArray) {

				int id = (int)((JSONObject)eachRepository).get("id");
				String repository = (String)((JSONObject)eachRepository).get("name");
				LocalDateTime pushed_at = changeStringToDateTime((String)((JSONObject)eachRepository).get("pushed_at"));
				if (repositoryIds.contains(Long.valueOf(id))) {
					userRepositoryDtoArrayList.add(
						new UserRepositoryDto(id, organizationName, repository, pushed_at, true));
				} else {
					userRepositoryDtoArrayList.add(
						new UserRepositoryDto(id, organizationName, repository, pushed_at, false));
				}
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

	public Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Long.parseLong((String)authentication.getPrincipal());
	}

	/**
	 * 현재 유저의 리포지토리 id, name을 return해주는 메소드
	 */
	public List<RepositoryInfoDto> getOAuthUserRepositoriesByUser(Long userId) {
		return getOAuthUserById(userId).getOAuthUserRepositoryTables().stream()
			.map(OAuthUserRepositoryTable::getRepository)
			.map(Repository::getRepositoryInfoDto)
			.toList();
	}

	public List<HistoryCollectorDto> registerRepository(RegisterRepositoryResponseDto registerRepositoryResponseDto,
		Long userId) {

		String token = getToken(userId);

		Set<GitOrganization> gitOrganizationSet = new HashSet<>();

		List<Repository> repositoryList = registerRepositoryResponseDto.getRepoDetailsList().stream()
			.map(repositoryDetail -> repositoryService.changeRepositoryDetailToRepository(token, gitOrganizationSet,
				repositoryDetail))
			.toList();
		gitOrganizationService.saveGitOrganizations(gitOrganizationSet);

		OAuthUser oAuthUser = getOAuthUserById(userId);
		List<HistoryCollectorDto> historyCollectorDtoList = getHistoryCollectorDtoList(token, repositoryList,
			oAuthUser);
		List<OAuthUserRepositoryTable> oAuthUserRepositoryTables = getoAuthUserRepositoryTables(
			repositoryList, oAuthUser);
		oAuthUser.setUserLevel(OauthUserLevel.AUTHED_HISTORY_COLLECT_NOT_ENDED_USER);
		oAuthUser.setOAuthUserRepositoryTables(oAuthUserRepositoryTables);
		repositoryService.saveAllRepositories(repositoryList);
		return historyCollectorDtoList;
	}

	private List<HistoryCollectorDto> getHistoryCollectorDtoList(String token, List<Repository> repositoryList,
		OAuthUser oAuthUser) {
		List<Repository> repositories = repositoryService.findRepositories(oAuthUser);
		return repositoryList.stream()
			.filter(repository -> !repositories.contains(repository))
			.peek(webhookSettingFunction(token))
			.map(getHistoryCollectorDtoFunction(token))
			.toList();
	}

	private Consumer<Repository> webhookSettingFunction(String token) {
		return repository -> setUserWebhookByRepoName(token,
			gitOrganizationService.findGitOrganizationById(repository.getOwnerOrganizationId()).getName(),
			repository.getName());
	}

	private Function<Repository, HistoryCollectorDto> getHistoryCollectorDtoFunction(String token) {
		return repository -> HistoryCollectorDto.builder()
			.token(token)
			.repoName(repository.getName())
			.ownerName(
				gitOrganizationService.findGitOrganizationById(repository.getOwnerOrganizationId()).getName())
			.build();
	}

	private List<OAuthUserRepositoryTable> getoAuthUserRepositoryTables(List<Repository> repositoryList,
		OAuthUser oAuthUser) {
		return repositoryList.stream()
			.map(repository -> oAuthUserRepositoryService
				.getOAuthUserRepositoryTable(oAuthUser, repository)
				.orElseGet(() -> OAuthUserRepositoryTable.builder()
					.oAuthUser(oAuthUser)
					.repository(repository)
					.build())
			)
			.toList();
	}

	private String setUserWebhookByRepoName(String token, String owner_name, String repo_name) {
		try {
			String body = "{\"config\": { \"url\": \"https://github-listener-nodejs.azurewebsites.net\", \"content_type\":\"'json'\", \"insecure_ssl\": \"'0'\" }, \"events\": [\"pull_request\", \"push\", \"label\", \"repository\", \"release\", \"issues\", \"create\", \"delete\", \"issue_comment\", \"pull_request_review_comment\"], \"active\": true}";
			return webClient.post()
				.uri(String.format("/repos/%s/%s/hooks", owner_name, repo_name))
				.header("Authorization", "Bearer " + token)
				.body(BodyInserters.fromValue(body))
				.exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
				.block();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getToken(Long userId) {
		return getOAuthUserById(userId).getOauthToken();
	}

	private OAuthUser getOAuthUserById(Long userId) {
		return oAuthRepository.findByUserRemoteId(userId)
			.orElseThrow(
				() -> new ConditionNotFoundException(userId + "에 해당하는 user Entity를 찾을 수 없습니다."));
	}

	public String getUserSession(String code, HttpServletRequest req) throws IOException {
		OAuthUser loginOAuthUser = saveUser(getLoginOAuthUser(code));
		LoginUser newLoginUser = new LoginUser(loginOAuthUser);
		return setHttpSession(req, newLoginUser);
	}

	public void logout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userRemoteId = Long.parseLong((String)authentication.getPrincipal());
		sessionMemory.loginUserHashMap.entrySet().stream()
			.filter((entry) -> entry.getValue().getUserRemoteId().equals(userRemoteId))
			.map(Map.Entry::getKey)
			.findAny()
			.ifPresent(key -> sessionMemory.loginUserHashMap.remove(key));
	}

	public OAuthUser saveUser(OAuthUser oAuthUser) {
		return oAuthRepository.findById(oAuthUser.getUserRemoteId())
			.orElseGet(() -> oAuthRepository.save(oAuthUser));
	}

	private String getUserInfo(String userGithubToken) throws IOException {
		URL url = new URL("https://api.github.com/user");
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Authorization", "Bearer ".concat(userGithubToken.replace("\"", "")));
		BufferedReader bufferedReader = null;
		String inputLine;
		if (http.getResponseCode() > 199 && http.getResponseCode() < 300) {
			bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
		}
		inputLine = bufferedReader.readLine();
		http.disconnect();
		return inputLine;
	}

	private OAuthUser getLoginOAuthUser(String code) throws IOException {
		return new OAuthUser(
			getUserInfo(getUserGitHubOAuthToken(code)),
			getUserGitHubOAuthToken(code));
	}

	private String setHttpSession(HttpServletRequest req, LoginUser newLoginUser) {
		HttpSession httpSession = req.getSession();
		httpSession.setAttribute("user", newLoginUser);
		sessionMemory.loginUserHashMap.put(httpSession.getId(), newLoginUser);
		return httpSession.getId();
	}

	private String getUserGitHubOAuthToken(String code) throws JsonProcessingException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		String clientId = deployProperties.clientId;
		String clientSecret = deployProperties.clientSecret;
		params.add("client_id", clientId);
		params.add("code", code);
		params.add("client_secret", clientSecret);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("https://github.com/login/oauth/access_token",
			HttpMethod.POST, entity, String.class);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		jsonNode = mapper.readTree(response.getBody());
		return String.valueOf(jsonNode.findValue("access_token"));
	}

	public Optional<OAuthUser> getUserById(Long userId) {
		return oAuthRepository.findById(userId);
	}
}