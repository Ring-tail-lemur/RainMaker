package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.TimeToRestoreService;
import com.ringtaillemur.rainmaker.domain.view.MttrDetail;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MttrBreakDownDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MttrDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.TimeToRestoreServiceDto;
import com.ringtaillemur.rainmaker.repository.MttrDetailRepository;
import com.ringtaillemur.rainmaker.repository.TimeToRestoreServiceRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeToRestoreServiceService {

	private final UtilService utilService;
	private final TimeToRestoreServiceRepository timeToRestoreServiceRepository;
	private final MttrDetailRepository mttrDetailRepository;

	public TimeToRestoreServiceDto getTimeToRestoreService(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<TimeToRestoreService> timeToRestoreServiceList = timeToRestoreServiceRepository.findByRepositoryIdInAndRestoredAtBetween(
			repositoryIds, startDateTime, endDateTime);

		Map<LocalDate, Double> dailyAverageTimeToRestoreServiceTimeMap = utilService.makeDailyAverageMap(
			timeToRestoreServiceList,
			timeToRestoreService -> timeToRestoreService.getRestoredAt().toLocalDate(),
			TimeToRestoreService::getRestoreServiceTime);

		return new TimeToRestoreServiceDto(startTime, endTime, dailyAverageTimeToRestoreServiceTimeMap);
	}

	public List<MttrDetailDto> getTimeToRestoreServiceDetail(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<MttrDetail> mttrDetailList = mttrDetailRepository.findByDataRepositoryIdInAndDataEndTimeBetween(
			repositoryIds, startDateTime, endDateTime);

		return mttrDetailList.stream().map(MttrDetailDto::new).toList();
	}

	public MttrBreakDownDto getMeanTimeToRecoverBreakDown(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		List<MttrDetailDto> timeToRestoreServiceDetail = getTimeToRestoreServiceDetail(repositoryIds, startTime,
			endTime);

		return new MttrBreakDownDto(timeToRestoreServiceDetail);
	}
}
