package com.ringtaillemur.rainmaker.controller.mocking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
		HashMap<LocalDate, Double> map = new HashMap<>();
		map.put(LocalDate.of(2022, 11, 11), 1050.0);
		map.put(LocalDate.of(2022, 11, 14), 297.0);
		map.put(LocalDate.of(2022, 11, 17), 542.0);
		map.put(LocalDate.of(2022, 11, 21), 570.0);
		map.put(LocalDate.of(2022, 11, 22), 471.0);
		map.put(LocalDate.of(2022, 11, 25), 714.0);
		return new TimeToRestoreServiceDto(startTime, endTime, map);
	}

	@GetMapping("/source")
	public List<MttrDetailDto> getTimeToRestoreServiceDetail(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		ArrayList<MttrDetailDto> data = new ArrayList<>();
		data.add(new MttrDetailDto("testUrl", 37L, 343L, 13L, 21L, 15L, 621L, 215L, 1050L));
		data.add(new MttrDetailDto("testUrl", 84L, 23L, 32L, 11L, 26L, 121L, 219L, 297L));
		data.add(new MttrDetailDto("testUrl", 91L, 113L, 62L, 32L, 33L, 211L, 225L, 542L));
		data.add(new MttrDetailDto("testUrl", 27L, 43L, 32L, 21L, 26L, 421L, 228L, 570L));
		data.add(new MttrDetailDto("testUrl", 51L, 172L, 15L, 21L, 41L, 171L, 231L, 471L));
		data.add(new MttrDetailDto("testUrl", 19L, 513L, 7L, 13L, 41L, 121L, 238L, 714L));
		return data;
	}

	@GetMapping("/break-down")
	public MttrBreakDownDto getMeanTimeToRecoverDetail(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return new MttrBreakDownDto(52, 1207, 27, 20, 30, 278);
	}
}
