package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.ReleaseSuccess;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.repository.ReleaseSuccessRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeploymentFrequencyService {

	private final ReleaseSuccessRepository releaseSuccessRepository;
	private final UtilService utilService;

	public DeploymentFrequencyDto getDeploymentFrequency(List<Long> repositoryIds, LocalDate startTime, LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ReleaseSuccess> releaseList = releaseSuccessRepository.findByRepositoryIdInAndReleasedAtBetween(repositoryIds,
			startDateTime, endDateTime);

		Map<LocalDate, Integer> dailyCountMap = utilService.makeDailyCountMap(releaseList,
			releaseSuccess -> releaseSuccess.getReleasedAt().toLocalDate());

		return new DeploymentFrequencyDto(startTime, endTime, dailyCountMap);
	}
}
