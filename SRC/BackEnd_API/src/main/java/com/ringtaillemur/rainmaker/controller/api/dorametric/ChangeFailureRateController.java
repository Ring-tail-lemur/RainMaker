package com.ringtaillemur.rainmaker.controller.api.dorametric;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.service.dorametrics.ChangeFailureRateService;

import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/dorametric/change-failure-rate")
@RequiredArgsConstructor
public class ChangeFailureRateController {
	private final ChangeFailureRateService changeFailureRateService;

	// @GetMapping
	public ChangeFailureRateDto doraChangeFailureRateApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return changeFailureRateService.getChangeFailureRate(repositoryIds, startTime, endTime);
	}

	// @GetMapping("/change-failure-rate-detail")
	public List<ChangeFailureRateDetailDto> getChangeFailureRateDetailBySource(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return changeFailureRateService.getChangeFailureRateDetailDto(repositoryIds, startTime, endTime);
	}

}
