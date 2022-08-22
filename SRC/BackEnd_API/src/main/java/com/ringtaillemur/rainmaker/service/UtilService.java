package com.ringtaillemur.rainmaker.service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

	public <T> Map<LocalDate, Integer> makeDailyCountMap(List<T> entityList,
		Function<T, LocalDate> getAnalysisLocalDate) {
		Map<LocalDate, List<T>> dailyValueListMap = entityList.stream()
			.collect(Collectors.groupingBy(getAnalysisLocalDate));
		return dailyValueListMap.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey, dailyValueListEntry -> dailyValueListEntry.getValue().size()));
	}

	public <T> Map<LocalDate, Double> makeDailyAverageMap(List<T> entityList,
		Function<T, LocalDate> getAnalysisLocalDate,
		Function<T, Number> getAnalysisValue) {
		Map<LocalDate, List<Number>> dailyValueListMap = getDailyValueListMap(entityList, getAnalysisLocalDate,
			getAnalysisValue);
		return getDailyAverageMap(dailyValueListMap);
	}

	private <T> Map<LocalDate, List<Number>> getDailyValueListMap(List<T> entityList,
		Function<T, LocalDate> getAnalysisLocalDate, Function<T, Number> getAnalysisValue) {
		Map<LocalDate, List<T>> dailyEntityListMap = entityList.stream()
			.collect(Collectors.groupingBy(getAnalysisLocalDate));
		return dailyEntityListMap.entrySet()
			.stream()
			.map(x -> new AbstractMap.SimpleEntry<>(
					x.getKey(),
					x.getValue()
						.stream()
						.map(getAnalysisValue)
						.collect(Collectors.toList())
				)
			)
			.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
	}

	private static Map<LocalDate, Double> getDailyAverageMap(Map<LocalDate, List<Number>> dailyValueListMap) {
		Stream<Map.Entry<LocalDate, List<Number>>> stream = dailyValueListMap.entrySet()
			.stream();
		return stream
			.collect(Collectors.toMap(Map.Entry::getKey,
				dailyValueListEntry -> dailyValueListEntry.getValue()
					.stream()
					.mapToDouble(Number::doubleValue)
					.average()
					.orElseThrow()));
	}
}
