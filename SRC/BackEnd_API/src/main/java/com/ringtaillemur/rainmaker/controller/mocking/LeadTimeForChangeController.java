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

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.CycleTimeDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.CycleTimeDetailsBySourcePrDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeDetailDto;
import com.ringtaillemur.rainmaker.service.dorametrics.LeadTimeForChangeService;

import lombok.RequiredArgsConstructor;

@RestController("/api/dorametric/lead-time-for-change")
@RequestMapping("/api/dorametric/lead-time-for-change")
@RequiredArgsConstructor
public class LeadTimeForChangeController {

	private final LeadTimeForChangeService leadTimeForChangeService;

	@GetMapping
	public LeadTimeForChangeByTimeDto doraLeadTimeForChangeForRestApi(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		HashMap<LocalDate, LeadTimeForChangeDetailDto> map = new HashMap<>();
		map.put(LocalDate.of(2022, 11, 11), new LeadTimeForChangeDetailDto(885, 84, 82, 112));
		map.put(LocalDate.of(2022, 11, 12), new LeadTimeForChangeDetailDto(670, 230, 462, 71));
		map.put(LocalDate.of(2022, 11, 13), new LeadTimeForChangeDetailDto(403, 451, 326, 80));
		map.put(LocalDate.of(2022, 11, 14), new LeadTimeForChangeDetailDto(90, 25, 53, 8));
		map.put(LocalDate.of(2022, 11, 15), new LeadTimeForChangeDetailDto(533, 12, 59, 5));
		map.put(LocalDate.of(2022, 11, 17), new LeadTimeForChangeDetailDto(410, 765, 135, 88));
		map.put(LocalDate.of(2022, 11, 19), new LeadTimeForChangeDetailDto(99, 15, 13, 11));
		map.put(LocalDate.of(2022, 11, 21), new LeadTimeForChangeDetailDto(1200, 891, 395, 135));
		map.put(LocalDate.of(2022, 11, 22), new LeadTimeForChangeDetailDto(86, 355, 21,52));
		map.put(LocalDate.of(2022, 11, 24), new LeadTimeForChangeDetailDto(119, 45, 123, 31));
		map.put(LocalDate.of(2022, 11, 25), new LeadTimeForChangeDetailDto(1110, 591, 195, 73));
		return new LeadTimeForChangeByTimeDto(startTime, endTime, map);
	}

	@GetMapping("/cycle-time-detail")
	public CycleTimeDetailDto cycleTimeDetailDto(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		return new CycleTimeDetailDto(4596L, 2923L, 167L, 61L);
	}

	@GetMapping("/cycle-time-detail/sources")
	public List<CycleTimeDetailsBySourcePrDto> getCycleTimeDetailsBySource(
		@RequestParam("start_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
		@RequestParam("end_time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
		@RequestParam("repo_id") List<Long> repositoryIds) {
		ArrayList<CycleTimeDetailsBySourcePrDto> data = new ArrayList<>();
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(985L, 52L, 72L, 111L), 661L, 6L, new HashMap<>(){{put("feat/login", 1109L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1383L, 35L, 92L, 72L), 18L, 1L, new HashMap<>(){{put("hotfix/login_duplication", 1510L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(388L, 136L, 35L, 153L), 431L, 1L, new HashMap<>(){{put("feat/register", 559L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(785L, 113L, 129L, 112L), 550L, 2L, new HashMap<>(){{put("feat/mttr-detail", 1027L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(611L, 128L, 661L, 50L), 88L, 3L, new HashMap<>(){{put("hotfix/mttr-error", 1400L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(729L, 332L, 263L, 92L), 445L, 6L, new HashMap<>(){{put("feat/lead-time-chart", 1324L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(742L, 611L, 130L, 86L), 322L, 2L, new HashMap<>(){{put("feat/lead-time-timer", 1483L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(164L, 291L, 522L, 74L), 81L, 3L, new HashMap<>(){{put("feat/slack-server-init", 977L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(144L, 30L, 43L, 14L), 32L, 4L, new HashMap<>(){{put("hotfix/slack-server-error", 217L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(36L, 20L, 63L, 2L), 16L, 7L, new HashMap<>(){{put("hotfix/slack-server-error2", 119L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(533L, 12L, 59L, 5L), 351L, 1L, new HashMap<>(){{put("feat/jira-connection", 604L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(312L, 965L, 153L, 52L), 155L, 3L, new HashMap<>(){{put("test/jira-test", 1430L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(476L, 535L, 207L, 124L), 5L, 6L, new HashMap<>(){{put("hotfix/jira-error", 1218L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(432L, 795L, 45L, 88L), 16L, 2L, new HashMap<>(){{put("hotfix/jira-error2", 1272L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(99L, 15L, 13L, 11L), 162L, 1L, new HashMap<>(){{put("doces/main-doces", 127L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1523L, 461L, 462L, 39L), 15L, 4L, new HashMap<>(){{put("feat/add-logger", 2436L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(877L, 1093L, 198L, 231L), 32L, 2L, new HashMap<>(){{put("hotfix/logger-error", 2168L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1131L, 689L, 595L, 113L), 85L, 8L, new HashMap<>(){{put("feat/slack-error-logger", 2528L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1269L, 1321L, 325L, 157L), 62L, 1L, new HashMap<>(){{put("core/github-action", 2915L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(136L, 315L, 32L,31L), 462L, 2L, new HashMap<>(){{put("feat/memory-save", 483L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(36L, 395L, 10L,73L), 62L, 3L, new HashMap<>(){{put("hotfix/component-error", 441L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(119L, 45L, 123L, 31L), 161L, 7L, new HashMap<>(){{put("feat/front-table", 127L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1206L, 612L, 206L, 54L), 1523L, 4L, new HashMap<>(){{put("feat/add-slack-jira", 2024L); put("main", -1L);}}));
		data.add(new CycleTimeDetailsBySourcePrDto("testUrl", new CycleTimeDetailDto(1014L, 570L, 184L, 92L), 120L, 2L, new HashMap<>(){{put("feat/add-connection-test", 1768L); put("main", -1L);}}));
		return data;
	}
}
