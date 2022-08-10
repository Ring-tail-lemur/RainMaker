package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.service.DoraMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class doraMetricsController {

	private final DoraMetricsService doraMetricsService;

	@ResponseBody
	@GetMapping("/dorametric/lead-time-for-change")
	public LeadTimeForChangeByTimeDto doraLeadTimeforChangeForRestApi(HttpServletRequest request) throws InterruptedException {

		String start_time1 = request.getParameter("start_time");
		String end_time1 = request.getParameter("end_time");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTimeLocalDate = LocalDate.parse(start_time1, formatter);
		LocalDate endTimeLocalDate = LocalDate.parse(end_time1, formatter);

		LocalDateTime start_time = startTimeLocalDate.atStartOfDay();
		LocalDateTime end_time = endTimeLocalDate.atStartOfDay();


		LeadTimeForChangeByTimeDto leadTimeForChangeByTime =
				doraMetricsService.getLeadTimeForChangeByTime(1L, start_time, end_time);
		return leadTimeForChangeByTime;
	}

	@ResponseBody
	@GetMapping("/dorametric/deployment-frequency")
	public DeploymentFrequencyDto doraDeploymentFrequencyForRestApi (HttpServletRequest request) throws InterruptedException {

		String start_time1 = request.getParameter("start_time");
		String end_time1 = request.getParameter("end_time");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTimeLocalDate = LocalDate.parse(start_time1, formatter);
		LocalDate endTimeLocalDate = LocalDate.parse(end_time1, formatter);

		LocalDateTime start_time = startTimeLocalDate.atStartOfDay();
		LocalDateTime end_time = endTimeLocalDate.atStartOfDay();

		DeploymentFrequencyDto frequencyDto = doraMetricsService.getDeploymentFrequencyByTimeAndRepo(1L, start_time, end_time);




		return frequencyDto;
	}

}
