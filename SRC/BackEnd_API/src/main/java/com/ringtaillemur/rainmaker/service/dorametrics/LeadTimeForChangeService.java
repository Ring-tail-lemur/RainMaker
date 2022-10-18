package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeDetailDto;
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

		Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap = getLocalDateLeadTimeForChangeDetailDtoMap(
			leadTimeForChangeList);

		return new LeadTimeForChangeByTimeDto(startTime, endTime, leadTimeForChangeDetailMap);
	}

	private Map<LocalDate, LeadTimeForChangeDetailDto> getLocalDateLeadTimeForChangeDetailDtoMap(
		List<LeadTimeForChange> leadTimeForChangeList) {
		Map<LocalDate, Double> codingTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate(),
			LeadTimeForChange::getCodingTimePart);

		Map<LocalDate, Double> pickupTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate(),
			LeadTimeForChange::getPickupTimePart);

		Map<LocalDate, Double> reviewTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate(),
			LeadTimeForChange::getReviewTimePart);

		Map<LocalDate, Double> deployTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate(),
			LeadTimeForChange::getDeploymentTimePart);

		Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap = new HashMap<>();
		for (LocalDate localDate : codingTimeMap.keySet()) {
			LeadTimeForChangeDetailDto leadTimeForChangeDetailDto = LeadTimeForChangeDetailDto.builder()
				.codingTime(codingTimeMap.get(localDate))
				.pickupTime(pickupTimeMap.get(localDate))
				.reviewTime(reviewTimeMap.get(localDate))
				.deployTime(deployTimeMap.get(localDate))
				.build();
			leadTimeForChangeDetailMap.put(localDate, leadTimeForChangeDetailDto);
		}

		return leadTimeForChangeDetailMap;
	}
}
