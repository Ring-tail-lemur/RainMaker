package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeadTimeForChangeService {

	private final LeadTimeForChangeRepository leadTimeForChangeRepository;
	private final UtilService utilService;

	public LeadTimeForChangeByTimeDto getLeadTimeForChangeByTime(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<LeadTimeForChange> leadTimeForChangeList = leadTimeForChangeRepository.findByRepositoryIdInAndDeploymentTimeBetween(
				repositoryIds, startDateTime, endDateTime);

		Map<LocalDate, Double> leadTimeForChangeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate(),
			LeadTimeForChange::getLeadTimeForChange);

		return new LeadTimeForChangeByTimeDto(startTime, endTime, leadTimeForChangeMap);
	}
}
