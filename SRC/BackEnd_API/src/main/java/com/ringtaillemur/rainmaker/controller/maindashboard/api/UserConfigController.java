package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.historycollectordto.HistoryCollector;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.ServerlessFunctionTriggerService;
import com.ringtaillemur.rainmaker.service.UserConfigService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserConfigController {
	private final UserConfigService userConfigService;
	private final ServerlessFunctionTriggerService serverlessFunctionTriggerService;

	@ResponseBody
	@GetMapping("/RepositorySelect")
	public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {
		return userConfigService.getUserRepositoryDtoByToken(userConfigService.getToken(userConfigService.getUserId()));
	}

	@ResponseBody
	@PostMapping("/RepositorySelect")
	public void userRepositoryRegisterRestAPI(@Valid @RequestBody RegisterRepoIdDto repoIds) {
		List<HistoryCollector> historyCollectorList = userConfigService.registerRepository(repoIds);
		serverlessFunctionTriggerService.triggerHistoryCollector(historyCollectorList);
	}
}
