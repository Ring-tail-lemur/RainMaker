package com.ringtaillemur.rainmaker.controller.user;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.service.UserConfigService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserConfigService userConfigService;
	@ResponseBody
	@PostMapping("/token")
	public String userRepositoryListReturnRestAPI(@RequestBody Map<String, String> requestBody) throws Exception {
		userConfigService.setOAuthToken(requestBody.get("token"));
		return requestBody.get("token");
	}

}
