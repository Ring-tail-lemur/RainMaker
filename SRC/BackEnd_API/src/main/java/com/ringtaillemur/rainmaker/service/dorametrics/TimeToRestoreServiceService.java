package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.TimeToRestoreService;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.TimeToRestoreServiceDto;
import com.ringtaillemur.rainmaker.repository.TimeToRestoreServiceRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeToRestoreServiceService {

	private final UtilService utilService;
	private final TimeToRestoreServiceRepository timeToRestoreServiceRepository;

	public TimeToRestoreServiceDto getTimeToRestoreService(Long repositoryId, LocalDate startTime, LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<TimeToRestoreService> timeToRestoreServiceList = timeToRestoreServiceRepository.findByRepositoryIdAndRestoredAtBetween(
			repositoryId, startDateTime, endDateTime);

		Map<LocalDate, Double> dailyAverageTimeToRestoreServiceTimeMap = utilService.makeDailyAverageMap(
			timeToRestoreServiceList,
			timeToRestoreService -> timeToRestoreService.getRestoredAt().toLocalDate(),
			TimeToRestoreService::getRestoreServiceTime);

		return new TimeToRestoreServiceDto(startTime, endTime, dailyAverageTimeToRestoreServiceTimeMap);
	}
}
