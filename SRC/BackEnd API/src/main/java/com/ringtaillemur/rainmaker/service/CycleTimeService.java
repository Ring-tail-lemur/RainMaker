package com.ringtaillemur.rainmaker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CycleTimeService {


	// public MainCycleTimeResponseDto getMainCycleTimeResponse() throws InterruptedException {
		// List<CycleTimeDto> targetCycleTimes = bigqueryCycleTimeRepository.findCycleTimesByCycleTimeEndBetween(
		// 	LocalDateTime.now().minusDays(7), LocalDateTime.now());
		// Map<String, Long> averageCycleTime = getAverageCycleTimeFromCycleTimeDto(targetCycleTimes);
		// return new MainCycleTimeResponseDto(averageCycleTime);
	// }

	private Map<String, Long> getAverageCycleTimeFromCycleTimeDto(List<CycleTimeDto> cycleTimes) {
		int cycleTimeSize = cycleTimes.size();
		Long totalCodingTime = 0L;
		Long totalPickupTime = 0L;
		Long totalReviewTime = 0L;
		Long totalDeploymentTime = 0L;

		for (CycleTimeDto cycleTime : cycleTimes) {
			totalCodingTime += cycleTime.getCodingTime();
			totalPickupTime += cycleTime.getPickupTime();
			totalReviewTime += cycleTime.getReviewTime();
			totalDeploymentTime += cycleTime.getDeploymentTime();
		}

		HashMap<String, Long> averageCycleTime = new HashMap<>();
		averageCycleTime.put("codingTime", totalCodingTime / cycleTimeSize);
		averageCycleTime.put("pickupTime", totalPickupTime / cycleTimeSize);
		averageCycleTime.put("reviewTime", totalReviewTime / cycleTimeSize);
		averageCycleTime.put("deploymentTime", totalDeploymentTime / cycleTimeSize);

		return averageCycleTime;
	}

	private Map<String, Long> getAverageCycleTimeFromCycleTimes(List<LeadTimeForChange> leadTimeForChanges) {
		int cycleTimeSize = leadTimeForChanges.size();
		Long totalCodingTime = 0L;
		Long totalPickupTime = 0L;
		Long totalReviewTime = 0L;
		Long totalDeploymentTime = 0L;

		for (LeadTimeForChange leadTimeForChange : leadTimeForChanges) {
			totalCodingTime += leadTimeForChange.getCodingTime();
			totalPickupTime += leadTimeForChange.getPickupTime();
			totalReviewTime += leadTimeForChange.getReviewTime();
			totalDeploymentTime += leadTimeForChange.getDeploymentTime();
		}

		HashMap<String, Long> averageCycleTime = new HashMap<>();
		averageCycleTime.put("codingTime", totalCodingTime / cycleTimeSize);
		averageCycleTime.put("pickupTime", totalPickupTime / cycleTimeSize);
		averageCycleTime.put("reviewTime", totalReviewTime / cycleTimeSize);
		averageCycleTime.put("deploymentTime", totalDeploymentTime / cycleTimeSize);

		return averageCycleTime;
	}
}
