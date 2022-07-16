package com.rainmaker.rainmakerwebserver.controller.maindashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rainmaker.rainmakerwebserver.dto.controllerdto.responsedto.MainCycleTimeResponseDto;
import com.rainmaker.rainmakerwebserver.service.CycleTimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class mainController {

	private final CycleTimeService cycleTimeService;

	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() {
		return cycleTimeService.getMainCycleTimeResponse();
	}
}
