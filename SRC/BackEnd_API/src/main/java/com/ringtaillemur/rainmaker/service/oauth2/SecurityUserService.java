package com.ringtaillemur.rainmaker.service.oauth2;

import static com.ringtaillemur.rainmaker.config.WebClientConfig.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ringtaillemur.rainmaker.config.JwtTokenProvider;
import com.ringtaillemur.rainmaker.config.UserAuthentication;
import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;

@Service
public class SecurityUserService {

	@Autowired
	OAuthRepository oAuthRepository;

	public Set<SimpleGrantedAuthority> setAuthorities(OauthUserLevel oauthUserLevel) {
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(oauthUserLevel.toString());
		grantedAuthorities.add(simpleGrantedAuthority);
		return grantedAuthorities;
	}

	public String getUserGitHubOAuthToken(String code) throws JsonProcessingException {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
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
		user.oauth_token = oAuthToken;

		return new OAuthUser(user.id, user.login, user.url, user.oauth_token, OauthUserLevel.FIRST_AUTH_USER);
	}

	public Optional<OAuthUser> checkDuplicationAndCommitUser(OAuthUser oAuthUser) {
		try {
			Optional<OAuthUser> presentUser = oAuthRepository.findByUserRemoteId(oAuthUser.getUserRemoteId());
			presentUser.get().update(oAuthUser.getOauthToken());
			oAuthRepository.save(presentUser.get());
			return presentUser;
		} catch (Exception e) {
			oAuthRepository.save(oAuthUser);
			return Optional.of(oAuthUser);
		}
	}

	public String setJwtTokenWithUserInfo(OAuthUser oAuthUser) {
		Authentication authentication = new UserAuthentication(String.valueOf(oAuthUser.getUserRemoteId()), null, null);
		return JwtTokenProvider.generateToken(authentication, oAuthUser.getUser_level());
	}

	public String setNewUpdateUserAuthorityJwtToken(String jwt, OauthUserLevel oauthUserLevel) {
		Long userRemoteIdFromJwt = Long.valueOf(JwtTokenProvider.getUserIdFromJWT(jwt));
		try {
			Optional<OAuthUser> oAuthUser = oAuthRepository.findByUserRemoteId(userRemoteIdFromJwt);
			oAuthUser.get().setUser_level(oauthUserLevel);
			oAuthRepository.save(oAuthUser.get());
			Authentication authentication = new UserAuthentication(String.valueOf(oAuthUser.get().getUserRemoteId()),
				null, null);
			return JwtTokenProvider.generateToken(authentication, oAuthUser.get().getUser_level());
		} catch (Exception e) {
			return "null";
		}
	}

	private static class JsonUser {
		String oauth_token;
		Long id;
		String url;
		String login;
	}

}
