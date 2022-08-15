package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

@Data
public class ChangeFailureRateDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, Integer> changeFailureRateMap = new HashMap<>();

	@Builder
	public ChangeFailureRateDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Integer> changeFailureRateMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.changeFailureRateMap = changeFailureRateMap;
		Integer averageLeadTimeForChange = getAverageLeadTimeForChange(changeFailureRateMap);
		this.level = getChangeFailureRateProductivityLevel(averageLeadTimeForChange);
	}

	private ProductivityLevel getChangeFailureRateProductivityLevel(Integer changeFailureRate) {
		if (changeFailureRate < 15) return ProductivityLevel.FRUIT;
		if (changeFailureRate < 46) return ProductivityLevel.SPROUT;
		return ProductivityLevel.SEED;
	}

	private Integer getAverageLeadTimeForChange(Map<LocalDate, Integer> leadTimeForChangeAverageMap){
		return (int) leadTimeForChangeAverageMap.values().stream()
			.mapToInt(leadTimeForChange -> leadTimeForChange)
			.average()
			.orElseThrow(() -> new NullArgumentException("nothing to average operation values"));
	}
}
