package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.config.WebClientConfig;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserConfigController {

	private final HttpSession session;

	private final UserConfigService userConfigService;

	@ResponseBody
	@GetMapping("/RepositorySelect")
	public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {

		// todo : UserId의 경우는 세션을 통해 알아올 것이고, token의 경우는 이 유저아이디를 통해 DB에서 빼올것.
		System.out.println(session.getId());
		Object oauth_user = session.getAttribute("OAUTH_USER");
		System.out.println(oauth_user);
		String token = "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";

		System.out.println(session);
		return userConfigService.getUserRepositoryDtoByToken(token);
	}

	@PostMapping("/RepositorySelect")
	public String userRepositoryRegisterRestAPI(@RequestParam(name = "repo_id") List<String> RepoInfos) {

		session.getId(); // 얘가 세션아이디
		session.getAttribute("OAUTH_USER");

		String token = "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";

		for (var repo : RepoInfos) {
			String[] repoArr = repo.split(","); // Id, Organ, Repo, Time 순.
			userConfigService.setUserWebhookByRepoName(token, repoArr[1], repoArr[2]);
		}

		// todo: repoId, token, repo Name, owner Name (Organization Name), 이걸로 azure function을 호출.
		return "redirect:" + WebClientConfig.FrontBaseUrl;
	}
}
