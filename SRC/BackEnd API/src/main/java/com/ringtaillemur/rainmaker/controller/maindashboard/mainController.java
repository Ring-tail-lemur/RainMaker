package com.ringtaillemur.rainmaker.controller.maindashboard;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.leadTimeForChangeByTimeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MainCycleTimeResponseDto;
import com.ringtaillemur.rainmaker.service.CycleTimeService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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
	public leadTimeForChangeByTimeDto mainDashboardCycleTimeThymeleaf(HttpServletRequest request) throws InterruptedException {

		String start_time1 = request.getParameter("start_time");
		String end_time1 = request.getParameter("end_time");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTimeLocalDate = LocalDate.parse(start_time1, formatter);
		LocalDate endTimeLocalDate = LocalDate.parse(end_time1, formatter);

		LocalDateTime start_time = startTimeLocalDate.atStartOfDay();
		LocalDateTime end_time = endTimeLocalDate.atStartOfDay();


		leadTimeForChangeByTimeDto leadTimeForChangeByTime =
				cycleTimeService.getLeadTimeForChangeByTime(1, start_time, end_time);
		// model.addAttribute("mainCycleTimeResponse", cycleTimeService.getMainCycleTimeResponse());
		return leadTimeForChangeByTime;
	}
}
