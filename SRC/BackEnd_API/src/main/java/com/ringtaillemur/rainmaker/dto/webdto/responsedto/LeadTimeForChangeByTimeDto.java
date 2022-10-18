package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.Map;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Data;

@Data
public class LeadTimeForChangeByTimeDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap;

	public LeadTimeForChangeByTimeDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.leadTimeForChangeDetailMap = leadTimeForChangeDetailMap;
		this.level = getLeadTimeForChangeProductivityLevel();
	}

	private ProductivityLevel getLeadTimeForChangeProductivityLevel() {
		double leadTimeForChange = getAverageLeadTimeForChange();
		if (leadTimeForChange < 1440)
			return ProductivityLevel.FRUIT;
		if (leadTimeForChange < 10080)
			return ProductivityLevel.FLOWER;
		if (leadTimeForChange < 43200)
			return ProductivityLevel.SPROUT;
		return ProductivityLevel.SEED;
	}

	private Double getAverageLeadTimeForChange() {
		return leadTimeForChangeDetailMap.values().stream()
			.mapToDouble(LeadTimeForChangeDetailDto::getTotalValue)
			.average()
			.orElse(0);
	}
}
