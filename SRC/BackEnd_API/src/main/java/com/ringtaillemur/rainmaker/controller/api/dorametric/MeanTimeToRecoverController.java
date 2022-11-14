package com.ringtaillemur.rainmaker.controller.api.dorametric;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MttrBreakDownDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MttrDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.TimeToRestoreServiceDto;
import com.ringtaillemur.rainmaker.service.dorametrics.TimeToRestoreServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dorametric/time-to-restore-service")
@RequiredArgsConstructor
public class MeanTimeToRecoverController {

	private final TimeToRestoreServiceService timeToRestoreServiceService;

	@GetMapping
	public TimeToRestoreServiceDto doraTimeToRestoreServiceApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return timeToRestoreServiceService.getTimeToRestoreService(repositoryIds, startTime, endTime);
	}

	@GetMapping("/source")
	public List<MttrDetailDto> getTimeToRestoreServiceDetail(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return timeToRestoreServiceService.getTimeToRestoreServiceDetail(repositoryIds, startTime, endTime);
	}

	@GetMapping("/break-down")
	public MttrBreakDownDto getMeanTimeToRecoverDetail(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return timeToRestoreServiceService.getMeanTimeToRecoverBreakDown(repositoryIds, startTime, endTime);
	}
}
