package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

@Data
public class LeadTimeForChangeByTimeDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, Double> leadTimeForChangeMap;


	public LeadTimeForChangeByTimeDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Double> leadTimeForChangeMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.leadTimeForChangeMap = leadTimeForChangeMap;
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

	private Double getAverageLeadTimeForChange(){
		return leadTimeForChangeMap.values().stream()
			.mapToDouble(leadTimeForChange -> leadTimeForChange)
			.average()
			.orElse(0);
	}
}
