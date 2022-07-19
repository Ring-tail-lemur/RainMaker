package com.rainmaker.rainmakerwebserver.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rainmaker.rainmakerwebserver.domain.jpadomain.analysis.delivery.CycleTime;
import com.rainmaker.rainmakerwebserver.dto.controllerdto.responsedto.MainCycleTimeResponseDto;
import com.rainmaker.rainmakerwebserver.dto.domaindto.CycleTimeDto;
import com.rainmaker.rainmakerwebserver.repository.BigqueryCycleTimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CycleTimeService {

	private final BigqueryCycleTimeRepository bigqueryCycleTimeRepository;

	public MainCycleTimeResponseDto getMainCycleTimeResponse() throws InterruptedException {
		List<CycleTimeDto> targetCycleTimes = bigqueryCycleTimeRepository.findCycleTimesByCycleTimeEndBetween(
			LocalDateTime.now().minusDays(7), LocalDateTime.now());
		Map<String, Long> averageCycleTime = getAverageCycleTimeFromCycleTimeDto(targetCycleTimes);
		return new MainCycleTimeResponseDto(averageCycleTime);
	}

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

	private Map<String, Long> getAverageCycleTimeFromCycleTimes(List<CycleTime> cycleTimes) {
		int cycleTimeSize = cycleTimes.size();
		Long totalCodingTime = 0L;
		Long totalPickupTime = 0L;
		Long totalReviewTime = 0L;
		Long totalDeploymentTime = 0L;

		for (CycleTime cycleTime : cycleTimes) {
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
}
