package com.ringtaillemur.rainmaker.controller.api.dorametric;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.service.dorametrics.LeadTimeForChangeService;

import lombok.RequiredArgsConstructor;

@RestController("/api/dorametric/lead-time-for-change")
@RequiredArgsConstructor
public class LeadTimeForChangeController {

	private final LeadTimeForChangeService leadTimeForChangeService;

	@GetMapping
	public LeadTimeForChangeByTimeDto doraLeadTimeForChangeForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return leadTimeForChangeService.getLeadTimeForChangeByTime(repositoryIds, startTime, endTime);
	}

}
