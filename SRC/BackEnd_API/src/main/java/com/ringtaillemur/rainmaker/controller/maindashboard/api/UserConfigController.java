package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import com.ringtaillemur.rainmaker.service.oauth2.SecurityUserService;
import com.ringtaillemur.rainmaker.util.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserConfigController {

	@Autowired
	private JwtUtils jwtUtils;

	private final HttpSession session;

	private final UserConfigService userConfigService;

	private final SecurityUserService securityUserService;

	@ResponseBody
	@GetMapping("/RepositorySelect")
	public List<UserRepositoryDto> userRepositoryListReturnRestAPI(HttpServletRequest request) {

		// todo : UserId의 경우는 세션을 통해 알아올 것이고, token의 경우는 이 유저아이디를 통해 DB에서 빼올것.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = String.valueOf(authentication.getPrincipal());
		String token = userConfigService.getToken(Long.valueOf(userId));

		return userConfigService.getUserRepositoryDtoByToken(token);
	}

	@PostMapping("/RepositorySelect")
	public String userRepositoryRegisterRestAPI( //@RequestBody String repoIds) {
		@RequestParam(name = "repo_id") List<String> repoIds, HttpServletRequest request,
		HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Now in POST(/RepositorySelect : " + authentication);

		for (var repoId : repoIds) {
			System.out.println(repoId);
		}

		// todo: repoId, token, repo Name, owner Name (Organization Name), 이걸로 azure function을 호출.
		// todo: Webhook 등록.
		String token = "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";
		String owner_name = "Ring-tail-lemur";
		String repo_name = "aa";

		userConfigService.setUserWebhookByRepoName(token, owner_name, repo_name);

		return "redirect:http://localhost:3000/";
	}
}
/*
todo 웹훅 등록
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/push" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/pull_request" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
*
* */
