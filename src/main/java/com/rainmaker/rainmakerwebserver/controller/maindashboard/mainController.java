package com.rainmaker.rainmakerwebserver.controller.maindashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rainmaker.rainmakerwebserver.dto.controllerdto.responsedto.MainCycleTimeResponseDto;
import com.rainmaker.rainmakerwebserver.service.CycleTimeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class mainController {

	private final CycleTimeService cycleTimeService;

	@ResponseBody
	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() throws InterruptedException {
		return cycleTimeService.getMainCycleTimeResponse();
	}

	@GetMapping("/cycletime")
	public String mainDashboardCycleTimeThymeleaf(Model model) throws InterruptedException {
		model.addAttribute("mainCycleTimeResponse", cycleTimeService.getMainCycleTimeResponse());
		return "cycletime";
	}
}
