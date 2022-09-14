package com.ringtaillemur.rainmaker.controller.maindashboard.api;


import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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

    @ResponseBody
    @GetMapping("/RepositorySelect")
    public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {
        return userConfigService.getUserRepositoryDtoByToken(userConfigService.getToken(userConfigService.getUserId()));
    }

    @PostMapping("/RepositorySelect")
    public String userRepositoryRegisterRestAPI(@RequestBody RegisterRepoIdDto repoIds) {

        userConfigService.registerRepository(repoIds);
        return "redirect:http://localhost:3000/";
    }
}
