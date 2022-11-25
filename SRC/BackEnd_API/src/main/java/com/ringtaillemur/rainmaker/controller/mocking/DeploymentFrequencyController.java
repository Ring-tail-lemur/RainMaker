package com.ringtaillemur.rainmaker.controller.mocking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.service.dorametrics.DeploymentFrequencyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dorametric/deployment-frequency")
@RequiredArgsConstructor
public class DeploymentFrequencyController {

	private final DeploymentFrequencyService deploymentFrequencyService;

	@GetMapping
	public DeploymentFrequencyDto doraDeploymentFrequencyForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {


		Map<LocalDate, Integer> map = new HashMap<>();
		map.put(LocalDate.of(2022, 11, 11), 4);
		map.put(LocalDate.of(2022, 11, 12), 2);
		map.put(LocalDate.of(2022, 11, 13), 2);
		map.put(LocalDate.of(2022, 11, 14), 2);
		map.put(LocalDate.of(2022, 11, 15), 1);
		map.put(LocalDate.of(2022, 11, 16), 0);
		map.put(LocalDate.of(2022, 11, 17), 3);
		map.put(LocalDate.of(2022, 11, 18), 0);
		map.put(LocalDate.of(2022, 11, 19), 1);
		map.put(LocalDate.of(2022, 11, 20), 0);
		map.put(LocalDate.of(2022, 11, 21), 4);
		map.put(LocalDate.of(2022, 11, 22), 2);
		map.put(LocalDate.of(2022, 11, 23), 0);
		map.put(LocalDate.of(2022, 11, 24), 1);
		map.put(LocalDate.of(2022, 11, 25), 2);
		map.put(LocalDate.of(2022, 11, 26), 0);
		return new DeploymentFrequencyDto(startTime, endTime, map);
	}

	@GetMapping("/deployment-frequency-detail")
	public List<DeploymentFrequencyDetailDto> getDeploymentFrequencyDetailBySource(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {

		List<DeploymentFrequencyDetailDto> data = new ArrayList<>();
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(661).commitSize(22).margin(0).repositoryName("loki").releaseName("v1.1.1").releaseDate(LocalDate.of(2022,11,11)).publishedAt("2022-11-11").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(18).commitSize(3).margin(0).repositoryName("loki").releaseName("v1.1.2").releaseDate(LocalDate.of(2022,11,11)).publishedAt("2022-11-11").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(431).commitSize(14).margin(0).repositoryName("loki").releaseName("v1.1.3").releaseDate(LocalDate.of(2022,11,11)).publishedAt("2022-11-11").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(550).commitSize(21).margin(100).repositoryName("loki").releaseName("v1.1.4").releaseDate(LocalDate.of(2022,11,11)).publishedAt("2022-11-11").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(88).commitSize(9).margin(0).repositoryName("loki").releaseName("v1.1.5").releaseDate(LocalDate.of(2022,11,12)).publishedAt("2022-11-12").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(445).commitSize(11).margin(50).repositoryName("loki").releaseName("v1.1.6").releaseDate(LocalDate.of(2022,11,12)).publishedAt("2022-11-12").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(322).commitSize(33).margin(0).repositoryName("loki").releaseName("v1.1.7").releaseDate(LocalDate.of(2022,11,13)).publishedAt("2022-11-13").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(81).commitSize(8).margin(50).repositoryName("loki").releaseName("v1.1.8").releaseDate(LocalDate.of(2022,11,13)).publishedAt("2022-11-13").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(32).commitSize(2).margin(0).repositoryName("loki").releaseName("v1.1.9").releaseDate(LocalDate.of(2022,11,14)).publishedAt("2022-11-14").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(16).commitSize(2).margin(50).repositoryName("loki").releaseName("v1.1.10").releaseDate(LocalDate.of(2022,11,14)).publishedAt("2022-11-14").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(351).commitSize(17).margin(100).repositoryName("loki").releaseName("v1.1.11").releaseDate(LocalDate.of(2022,11,15)).publishedAt("2022-11-15").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(115).commitSize(12).margin(0).repositoryName("loki").releaseName("v1.1.12").releaseDate(LocalDate.of(2022,11,17)).publishedAt("2022-11-17").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(5).commitSize(1).margin(0).repositoryName("loki").releaseName("v1.1.13").releaseDate(LocalDate.of(2022,11,17)).publishedAt("2022-11-17").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(16).commitSize(3).margin(100).repositoryName("loki").releaseName("v1.1.14").releaseDate(LocalDate.of(2022,11,17)).publishedAt("2022-11-17").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(162).commitSize(12).margin(100).repositoryName("loki").releaseName("v1.1.15").releaseDate(LocalDate.of(2022,11,19)).publishedAt("2022-11-19").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(15).commitSize(2).margin(0).repositoryName("loki").releaseName("v1.1.16").releaseDate(LocalDate.of(2022,11,21)).publishedAt("2022-11-21").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(32).commitSize(4).margin(0).repositoryName("loki").releaseName("v1.1.17").releaseDate(LocalDate.of(2022,11,21)).publishedAt("2022-11-21").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(85).commitSize(11).margin(0).repositoryName("loki").releaseName("v1.1.18").releaseDate(LocalDate.of(2022,11,21)).publishedAt("2022-11-21").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(62).commitSize(7).margin(50).repositoryName("loki").releaseName("v1.1.19").releaseDate(LocalDate.of(2022,11,21)).publishedAt("2022-11-21").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(462).commitSize(21).margin(0).repositoryName("loki").releaseName("v1.1.20").releaseDate(LocalDate.of(2022,11,22)).publishedAt("2022-11-22").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(62).commitSize(5).margin(100).repositoryName("loki").releaseName("v1.1.21").releaseDate(LocalDate.of(2022,11,22)).publishedAt("2022-11-22").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(161).commitSize(12).margin(50).repositoryName("loki").releaseName("v1.1.22").releaseDate(LocalDate.of(2022,11,24)).publishedAt("2022-11-24").build());

		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(1523).commitSize(45).margin(50).repositoryName("loki").releaseName("v1.2.0").releaseDate(LocalDate.of(2022,11,25)).publishedAt("2022-11-25").build());
		data.add(DeploymentFrequencyDetailDto.builder().codeChangeSize(120).commitSize(4).margin(50).repositoryName("loki").releaseName("v1.2.1").releaseDate(LocalDate.of(2022,11,25)).publishedAt("2022-11-25").build());
		return data;
	}
}
