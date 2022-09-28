package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.ReleaseSuccess;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.repository.ReleaseSuccessRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeFailureRateService {

	private final ReleaseSuccessRepository releaseSuccessRepository;
	private final UtilService utilService;

	public ChangeFailureRateDto getChangeFailureRate(List<Long> repositoryIds, LocalDate startTime, LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ReleaseSuccess> releaseSuccessList = releaseSuccessRepository.findByRepositoryIdInAndReleasedAtBetween
			(repositoryIds, startDateTime, endDateTime);

		Map<LocalDate, Double> changeFailureRateMap = utilService.makeDailyAverageMap(releaseSuccessList,
			releaseSuccess -> releaseSuccess.getReleasedAt().toLocalDate(),
			ReleaseSuccess::getIsSuccessLongValue);

		return new ChangeFailureRateDto(startTime, endTime, changeFailureRateMap);
	}
}
