package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.Map;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Data;

@Data
public class ChangeFailureRateDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, Double> changeFailureRateMap;

	public ChangeFailureRateDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Double> changeFailureRateMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.changeFailureRateMap = changeFailureRateMap;
		this.level = getChangeFailureRateProductivityLevel();
	}

	private ProductivityLevel getChangeFailureRateProductivityLevel() {
		double changeFailureRate = getAverageFailureRate();
		if (changeFailureRate < 0.15)
			return ProductivityLevel.FRUIT;
		if (changeFailureRate < 0.46)
			return ProductivityLevel.SPROUT;
		return ProductivityLevel.SEED;
	}

	private Double getAverageFailureRate() {
		return changeFailureRateMap.values().stream()
			.mapToDouble(changeFailureRate -> changeFailureRate)
			.average()
			.orElse(0);
	}
}
