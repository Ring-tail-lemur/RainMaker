package com.ringtaillemur.rainmaker.controller.maindashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MainCycleTimeResponseDto;
import com.ringtaillemur.rainmaker.service.CycleTimeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class mainController {

	private final CycleTimeService cycleTimeService;

	@ResponseBody
	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() throws InterruptedException {
		// return cycleTimeService.getMainCycleTimeResponse();
		return new MainCycleTimeResponseDto();
	}

	@GetMapping("/cycletime")
	public String mainDashboardCycleTimeThymeleaf(Model model) throws InterruptedException {
		// model.addAttribute("mainCycleTimeResponse", cycleTimeService.getMainCycleTimeResponse());
		return "cycletime";
	}
}
