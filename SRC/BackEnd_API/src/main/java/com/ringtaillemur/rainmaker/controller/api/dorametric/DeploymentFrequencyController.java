package com.ringtaillemur.rainmaker.controller.api.dorametric;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.service.dorametrics.DeploymentFrequencyService;

import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/dorametric/deployment-frequency")
@RequiredArgsConstructor
public class DeploymentFrequencyController {

	private final DeploymentFrequencyService deploymentFrequencyService;

	// @GetMapping
	public DeploymentFrequencyDto doraDeploymentFrequencyForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return deploymentFrequencyService.getDeploymentFrequency(repositoryIds, startTime, endTime);
	}

	// @GetMapping("/deployment-frequency-detail")
	public List<DeploymentFrequencyDetailDto> getDeploymentFrequencyDetailBySource(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return deploymentFrequencyService.getDeploymentFrequencyDetailDto(repositoryIds, startTime, endTime);
	}
}
