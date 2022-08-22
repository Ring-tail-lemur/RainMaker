package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
// import com.ringtaillemur.rainmaker.service.dorametrics.ChangeFailureRateService;
import com.ringtaillemur.rainmaker.service.dorametrics.ChangeFailureRateService;
import com.ringtaillemur.rainmaker.service.dorametrics.DeploymentFrequencyService;
import com.ringtaillemur.rainmaker.service.dorametrics.LeadTimeForChangeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DoraMetricsController {

	private final LeadTimeForChangeService leadTimeForChangeService;
	private final DeploymentFrequencyService deploymentFrequencyService;
	private final ChangeFailureRateService changeFailureRateService;

	@ResponseBody
	@GetMapping("/dorametric/lead-time-for-change")
	public LeadTimeForChangeByTimeDto doraLeadTimeForChangeForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") Long repoId) {
		return leadTimeForChangeService.getLeadTimeForChangeByTime(repoId, startTime, endTime);
	}

	@ResponseBody
	@GetMapping("/dorametric/deployment-frequency")
	public DeploymentFrequencyDto doraDeploymentFrequencyForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime) {
		return deploymentFrequencyService.getDeploymentFrequency(1L, startTime, endTime);
	}

	@ResponseBody
	@GetMapping("/dorametric/change-failure-rate")
	public ChangeFailureRateDto doraChangeFailureRateApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime) {
		return changeFailureRateService.getChangeFailureRate(1L, startTime, endTime);
	}
}
