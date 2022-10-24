package com.ringtaillemur.rainmaker.service.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.configdto.DeployProperties;
import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;
import com.ringtaillemur.rainmaker.dto.securitydto.SessionMemory;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserService {

	private final OAuthRepository oAuthRepository;
	private final DeployProperties deployProperties;
	private final SessionMemory sessionMemory;

	public String getUserSession(String code, HttpServletRequest req) throws IOException {
		String userGithubToken = getUserGitHubOAuthToken(code);

		//http GET을 통하여 Github 고유 JWT 가져오는 메서드
		String userInfoLine = getUserInfoWithToken(userGithubToken);
		// userInfoLine -> OauthUser로 변환(OauthUserLevel == FIRST_AUTH_USER)
		OAuthUser nowLoginUser = stringToUserFirstAuthUserEntity(
			userInfoLine, userGithubToken.replace("\"", ""));
		//Repository 에 들어가 있는 상태의 OauthUser Entity 리턴
		Optional<OAuthUser> nowUser = checkDuplicationAndCommitUser(nowLoginUser);

		LoginUser newLoginUser = new LoginUser(nowUser.get());
		HttpSession httpSession = req.getSession();
		httpSession.setAttribute("user", newLoginUser);
		sessionMemory.loginUserHashMap.put(httpSession.getId(), newLoginUser);
		return httpSession.getId();
	}

	public void logout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String tmp = null;
		Long userRemoteId = Long.parseLong((String)authentication.getPrincipal());
		for (String key : sessionMemory.loginUserHashMap.keySet()) {
			Long sessionRemoteId = sessionMemory.loginUserHashMap.get(key).getUserRemoteId();
			if (userRemoteId.equals(sessionRemoteId)) {
				tmp = key;
				break;
			}
		}
		if (tmp != null) {
			sessionMemory.loginUserHashMap.remove(tmp);
		}
	}

	public Set<SimpleGrantedAuthority> setAuthorities(OauthUserLevel oauthUserLevel) {
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(oauthUserLevel.toString());
		grantedAuthorities.add(simpleGrantedAuthority);
		return grantedAuthorities;
	}

	public String getUserGitHubOAuthToken(String code) throws JsonProcessingException {
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

	public String getUserInfoWithToken(String userGithubToken) throws IOException {
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

	public OAuthUser stringToUserFirstAuthUserEntity(String userInfoLine, String oAuthToken) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();

		JsonUser user = gson.fromJson(userInfoLine, JsonUser.class);
		user.oauthToken = oAuthToken;

		return new OAuthUser(user.id, user.login, user.url, user.oauthToken, OauthUserLevel.FIRST_AUTH_USER);
	}

	public Optional<OAuthUser> checkDuplicationAndCommitUser(OAuthUser oAuthUser) {
		if (oAuthUser == null) {
			return Optional.empty();
		}
		Optional<OAuthUser> presentUser = oAuthRepository.findByUserRemoteId(oAuthUser.getUserRemoteId());
		if (presentUser.isPresent()) {
			return presentUser;
		}
		oAuthRepository.save(oAuthUser);
		return Optional.of(oAuthUser);
	}

	private static class JsonUser {
		String oauthToken;
		Long id;
		String url;
		String login;
	}

}
