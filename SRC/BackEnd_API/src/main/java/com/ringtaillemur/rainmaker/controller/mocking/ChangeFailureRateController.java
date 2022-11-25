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

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.service.dorametrics.ChangeFailureRateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dorametric/change-failure-rate")
@RequiredArgsConstructor
public class ChangeFailureRateController {
	private final ChangeFailureRateService changeFailureRateService;

	@GetMapping
	public ChangeFailureRateDto doraChangeFailureRateApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {

		Map<LocalDate, Double> changeFailureRateMap = new HashMap<>();
		changeFailureRateMap.put(LocalDate.of(2022, 11, 11), 0.75);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 12), 0.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 13), 0.5);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 14), 0.5);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 15), 1.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 16), 0.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 17), 0.66);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 18), 0.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 19), 1.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 20), 0.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 21), 0.75);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 22), 0.5);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 23), 0.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 24), 1.0);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 25), 0.5);
		changeFailureRateMap.put(LocalDate.of(2022, 11, 26), 0.0);
		return new ChangeFailureRateDto(startTime, endTime, changeFailureRateMap);
	}



	@GetMapping("/change-failure-rate-detail")
	public List<ChangeFailureRateDetailDto> getChangeFailureRateDetailBySource(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		List<ChangeFailureRateDetailDto> data = new ArrayList<>();
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(661).commitSize(22).isSuccess(true).margin(0).releaseName("v1.1.1").releaseDate(LocalDate.of(2022,11,11)).repositoryName("loki").publishedAt("2022-11-11").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(18).commitSize(3).isSuccess(true).margin(0).releaseName("v1.1.2").releaseDate(LocalDate.of(2022,11,11)).repositoryName("loki").publishedAt("2022-11-11").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(431).commitSize(14).isSuccess(true).margin(0).releaseName("v1.1.3").releaseDate(LocalDate.of(2022,11,11)).repositoryName("loki").publishedAt("2022-11-11").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(550).commitSize(21).isSuccess(false).margin(100).releaseName("v1.1.4").releaseDate(LocalDate.of(2022,11,11)).repositoryName("loki").publishedAt("2022-11-11").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(88).commitSize(9).isSuccess(false).margin(0).releaseName("v1.1.5").releaseDate(LocalDate.of(2022,11,12)).repositoryName("loki").publishedAt("2022-11-12").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(445).commitSize(11).isSuccess(false).margin(50).releaseName("v1.1.6").releaseDate(LocalDate.of(2022,11,12)).repositoryName("loki").publishedAt("2022-11-12").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(322).commitSize(33).isSuccess(false).margin(0).releaseName("v1.1.7").releaseDate(LocalDate.of(2022,11,13)).repositoryName("loki").publishedAt("2022-11-13").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(81).commitSize(8).isSuccess(true).margin(50).releaseName("v1.1.8").releaseDate(LocalDate.of(2022,11,13)).repositoryName("loki").publishedAt("2022-11-13").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(32).commitSize(2).isSuccess(false).margin(0).releaseName("v1.1.9").releaseDate(LocalDate.of(2022,11,14)).repositoryName("loki").publishedAt("2022-11-14").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(16).commitSize(2).isSuccess(true).margin(50).releaseName("v1.1.10").releaseDate(LocalDate.of(2022,11,14)).repositoryName("loki").publishedAt("2022-11-14").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(351).commitSize(17).isSuccess(true).margin(100).releaseName("v1.1.11").releaseDate(LocalDate.of(2022,11,15)).repositoryName("loki").publishedAt("2022-11-15").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(155).commitSize(12).isSuccess(true).margin(0).releaseName("v1.1.12").releaseDate(LocalDate.of(2022,11,17)).repositoryName("loki").publishedAt("2022-11-17").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(5).commitSize(1).isSuccess(true).margin(0).releaseName("v1.1.13").releaseDate(LocalDate.of(2022,11,17)).repositoryName("loki").publishedAt("2022-11-17").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(16).commitSize(3).isSuccess(false).margin(100).releaseName("v1.1.14").releaseDate(LocalDate.of(2022,11,17)).repositoryName("loki").publishedAt("2022-11-17").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(162).commitSize(12).isSuccess(true).margin(100).releaseName("v1.1.15").releaseDate(LocalDate.of(2022,11,19)).repositoryName("loki").publishedAt("2022-11-19").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(15).commitSize(2).isSuccess(false).margin(0).releaseName("v1.1.16").releaseDate(LocalDate.of(2022,11,21)).repositoryName("loki").publishedAt("2022-11-21").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(32).commitSize(4).isSuccess(false).margin(0).releaseName("v1.1.17").releaseDate(LocalDate.of(2022,11,21)).repositoryName("loki").publishedAt("2022-11-21").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(85).commitSize(11).isSuccess(false).margin(0).releaseName("v1.1.18").releaseDate(LocalDate.of(2022,11,21)).repositoryName("loki").publishedAt("2022-11-21").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(62).commitSize(7).isSuccess(true).margin(50).releaseName("v1.1.19").releaseDate(LocalDate.of(2022,11,21)).repositoryName("loki").publishedAt("2022-11-21").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(462).commitSize(21).isSuccess(true).margin(0).releaseName("v1.1.20").releaseDate(LocalDate.of(2022,11,22)).repositoryName("loki").publishedAt("2022-11-22").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(62).commitSize(5).isSuccess(false).margin(100).releaseName("v1.1.21").releaseDate(LocalDate.of(2022,11,22)).repositoryName("loki").publishedAt("2022-11-22").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(161).commitSize(12).isSuccess(true).margin(50).releaseName("v1.1.22").releaseDate(LocalDate.of(2022,11,24)).repositoryName("loki").publishedAt("2022-11-24").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(1523).commitSize(45).isSuccess(false).margin(50).releaseName("v1.2.0").releaseDate(LocalDate.of(2022,11,25)).repositoryName("loki").publishedAt("2022-11-25").build());
		data.add(ChangeFailureRateDetailDto.builder().codeChangeSize(120).commitSize(4).isSuccess(true).margin(50).releaseName("v1.2.1").releaseDate(LocalDate.of(2022,11,25)).repositoryName("loki").publishedAt("2022-11-25").build());
		return data;
	}

}
