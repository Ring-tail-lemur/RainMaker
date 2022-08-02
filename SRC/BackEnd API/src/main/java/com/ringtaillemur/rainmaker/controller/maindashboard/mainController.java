package com.ringtaillemur.rainmaker.controller.maindashboard;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.leadTimeForChangeByTimeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MainCycleTimeResponseDto;
import com.ringtaillemur.rainmaker.service.CycleTimeService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class mainController {

	private final CycleTimeService cycleTimeService;

	@ResponseBody
	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() throws InterruptedException {
//		cycleTimeService.test();
		return new MainCycleTimeResponseDto();
	}

	@ResponseBody
	@GetMapping("/frontfor/cycletime")
	public leadTimeForChangeByTimeDto mainDashboardCycleTimeThymeleaf(Model model) throws InterruptedException {

		LocalDateTime start_time = LocalDateTime.now();
		LocalDateTime end_time = LocalDateTime.parse("2022-08-03 22:59:59",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


		leadTimeForChangeByTimeDto leadTimeForChangeByTime =
				cycleTimeService.getLeadTimeForChangeByTime(1, start_time, end_time);
		// model.addAttribute("mainCycleTimeResponse", cycleTimeService.getMainCycleTimeResponse());
		return leadTimeForChangeByTime;
	}
}
