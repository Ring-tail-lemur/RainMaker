package com.ringtaillemur.rainmaker.controller.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollectorDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepositoryResponseDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryInfoDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.ServerlessFunctionApiService;
import com.ringtaillemur.rainmaker.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final ServerlessFunctionApiService serverlessFunctionApiService;

	private final UserService userService;

	@GetMapping("/check")
	public void checkLoginController() {}

	@GetMapping("/repositories")
	public List<RepositoryInfoDto> getUserRegisteredRepositoryInfo() {
		Long userId = userService.getCurrentUserId();
		return userService.getOAuthUserRepositoriesByUser(userId);
	}

	@PostMapping("/repositories")
	public void userRepositoryRegisterRestAPI(@Valid @RequestBody RegisterRepositoryResponseDto repoIds) {
		List<HistoryCollectorDto> historyCollectorDtoList = userService.registerRepository(repoIds,
			userService.getCurrentUserId());
		serverlessFunctionApiService.triggerHistoryCollector(historyCollectorDtoList);
	}

	@GetMapping("/remote/repositories")
	public List<UserRepositoryDto> userRepositoryListReturnRestAPI() throws Exception {
		return userService.getUserRepositoryDtoByToken(userService.getToken(userService.getCurrentUserId()));
	}

	@PostMapping("/token")
	public void userRepositoryListReturnRestAPI(@RequestBody Map<String, String> requestBody) throws Exception {
		userService.setOAuthToken(requestBody.get("token"));
	}

	@PostMapping("/slack/url")
	public void userSlackUrlRegister(@RequestBody Map<String, String> requestBody) throws Exception {
		userService.setSlackUrl(requestBody.get("slackUrl"));
	}

	@GetMapping("/login/oauth2/code/github")
	public String githubLogin(
		@RequestParam(value = "code", required = false, defaultValue = "test") String code,
		HttpServletRequest req
	) throws IOException {
		return userService.getUserSession(code, req);
	}

	@GetMapping("/logout")
	public void logOutWithUserId() {
		userService.logout();
	}
}
