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
	private Map<LocalDate, Integer> leadTimeForChangeAverageMap = new HashMap<>();

	@Builder
	public LeadTimeForChangeByTimeDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Integer> leadTimeForChangeAverageMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.leadTimeForChangeAverageMap = leadTimeForChangeAverageMap;
		Integer averageLeadTimeForChange = getAverageLeadTimeForChange(leadTimeForChangeAverageMap);
		this.level = getLeadTimeForChangeProductivityLevel(averageLeadTimeForChange);
	}

	private ProductivityLevel getLeadTimeForChangeProductivityLevel(Integer leadTimeForChange) {
		if (leadTimeForChange < 1440)
			return ProductivityLevel.FRUIT;
		if (leadTimeForChange < 10080)
			return ProductivityLevel.FLOWER;
		if (leadTimeForChange < 43200)
			return ProductivityLevel.SPROUT;
		return ProductivityLevel.SEED;
	}

	private Integer getAverageLeadTimeForChange(Map<LocalDate, Integer> leadTimeForChangeAverageMap){
		return (int) leadTimeForChangeAverageMap.values().stream()
			.mapToInt(leadTimeForChange -> leadTimeForChange)
			.average()
			.orElseThrow(() -> new NullArgumentException("nothing to average operation values"));
	}
}
