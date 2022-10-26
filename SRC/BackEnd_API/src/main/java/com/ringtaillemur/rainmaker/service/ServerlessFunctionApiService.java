package com.ringtaillemur.rainmaker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollectorDto;
import com.ringtaillemur.rainmaker.repository.OAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServerlessFunctionApiService {

	private final OAuthRepository oAuthRepository;
	private final UserService userService;

	public void triggerHistoryCollector(List<HistoryCollectorDto> historyCollectorDtoList) {
		final Long userId = userService.getCurrentUserId();
		if (checkHistoryCollectorListIsNull(historyCollectorDtoList, userId))
			return;

		try {
			WebClient ServerlessFunctionClient = WebClient.builder()
				.baseUrl("https://github-history-collector.azurewebsites.net")
				.build();
			ServerlessFunctionClient.post()
				.uri("/api/HttpExample")
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(historyCollectorDtoList)
				.exchange()
				.flux()
				.subscribe((result) -> changeAuthority(result, userId));
		} catch (Exception e) {
			throw new RuntimeException("History Collector를 불러오는데 실패했습니다." + e.getMessage());
		}
	}

	private boolean checkHistoryCollectorListIsNull(List<HistoryCollectorDto> historyCollectorDtoList, Long userId) {
		if (historyCollectorDtoList.isEmpty()) {
			Optional<OAuthUser> id = oAuthRepository.findById(userId);
			OAuthUser oAuthUser = id.orElseThrow();
			oAuthUser.setUserLevel(OauthUserLevel.AUTHED_HISTORY_COLLECT_ENDED_USER);
			userService.saveUser(oAuthUser);
			return true;
		}
		return false;
	}

	private void changeAuthority(ClientResponse result, Long userId) {
		HttpStatus httpStatus = result.statusCode();
		if (httpStatus.is2xxSuccessful()) {
			Optional<OAuthUser> id = oAuthRepository.findById(userId);
			OAuthUser oAuthUser = id.orElseThrow();
			oAuthUser.setUserLevel(OauthUserLevel.AUTHED_HISTORY_COLLECT_ENDED_USER);
			oAuthRepository.save(oAuthUser);
		} else if (!httpStatus.is2xxSuccessful()) {
			OAuthUser loginUser = oAuthRepository.findById(userId).orElseThrow();
			loginUser.setUserLevel(OauthUserLevel.FAILED);
			oAuthRepository.save(loginUser);
		}
	}

}
