package com.ringtaillemur.rainmaker.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollector;
import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;
import com.ringtaillemur.rainmaker.dto.securitydto.SessionMemory;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryInfoDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.ServerlessFunctionTriggerService;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import com.ringtaillemur.rainmaker.service.oauth2.SecurityUserService;

import lombok.RequiredArgsConstructor;

@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserConfigService userConfigService;
	private final ServerlessFunctionTriggerService serverlessFunctionTriggerService;

	private final SecurityUserService securityUserService;

	@GetMapping("/check")
	public void checkLoginController() {}

	@GetMapping("/repositories")
	public List<RepositoryInfoDto> getUserRegisteredRepositoryInfo() {
		return userConfigService.getOAuthUserRepositoriesByUser();
	}

	@PostMapping("/repositories")
	public void userRepositoryRegisterRestAPI(@Valid @RequestBody RegisterRepoIdDto repoIds) {
		List<HistoryCollector> historyCollectorList = userConfigService.registerRepository(repoIds);
		serverlessFunctionTriggerService.triggerHistoryCollector(historyCollectorList);
	}

	@GetMapping("/remote/repositories")
	public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {
		return userConfigService.getUserRepositoryDtoByToken(userConfigService.getToken(userConfigService.getUserId()));
	}

	@PostMapping("/token")
	public void userRepositoryListReturnRestAPI(@RequestBody Map<String, String> requestBody) throws Exception {
		userConfigService.setOAuthToken(requestBody.get("token"));
	}

	@GetMapping("/login/oauth2/code/github")
	public String checkGithubLogin(
		@RequestParam(value = "code", required = false, defaultValue = "test") String code,
		HttpServletRequest req
	) throws IOException {
		return securityUserService.getUserSession(code, req);
	}

	@GetMapping("/logout")
	public void logOutWithUserId() {
		securityUserService.logout();
	}
}
