package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.Map;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Data;

@Data
public class TimeToRestoreServiceDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, Double> timeToRestoreServiceMap;

	public TimeToRestoreServiceDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Double> timeToRestoreServiceMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeToRestoreServiceMap = timeToRestoreServiceMap;
		this.level = getTimeToRestoreServiceProductivityLevel();
	}

	private ProductivityLevel getTimeToRestoreServiceProductivityLevel() {
		double timeToRestoreService = getTimeToRestoreService();
		if (timeToRestoreService < 60)
			return ProductivityLevel.FRUIT;
		if (timeToRestoreService < 1440)
			return ProductivityLevel.FLOWER;
		if (timeToRestoreService < 10080)
			return ProductivityLevel.SPROUT;
		return ProductivityLevel.SEED;
	}

	private double getTimeToRestoreService() {
		return timeToRestoreServiceMap.values().stream()
			.mapToDouble(deploymentFrequency -> deploymentFrequency)
			.average()
			.orElse(0);
	}
}
