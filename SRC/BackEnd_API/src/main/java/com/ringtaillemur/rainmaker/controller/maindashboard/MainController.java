package com.ringtaillemur.rainmaker.controller.maindashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MainCycleTimeResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {


	@ResponseBody
	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() {
		return new MainCycleTimeResponseDto();
	}
}
