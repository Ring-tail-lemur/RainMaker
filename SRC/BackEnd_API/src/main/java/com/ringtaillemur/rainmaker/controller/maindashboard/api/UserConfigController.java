package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import com.ringtaillemur.rainmaker.util.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserConfigController {


	private final JwtUtils jwtUtils;

	private final HttpSession session;

	private final UserConfigService userConfigService;

	@ResponseBody
	@GetMapping("/RepositorySelect")
	public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI(HttpServletRequest req) {
			return userConfigService.getUserRepositoryDtoByToken(userConfigService.getToken(userConfigService.getUserId()));
	}

	@ResponseBody
	@PostMapping("/RepositorySelect")
	public void userRepositoryRegisterRestAPI(@RequestBody RegisterRepoIdDto repoIds) {
		userConfigService.registerRepository(repoIds);
		return;
	}
}
