package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.FailedChange;
import com.ringtaillemur.rainmaker.domain.Release;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.repository.FailedChangeRepository;
import com.ringtaillemur.rainmaker.repository.ReleaseRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeFailureRateService {

	private final FailedChangeRepository failedChangeRepository;
	private final RepositoryRepository repositoryRepository;
	private final ReleaseRepository releaseRepository;

	public ChangeFailureRateDto getChangeFailureRate(Long repositoryId, LocalDate startTime, LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		Repository repository = repositoryRepository.findById(repositoryId)
			.orElseThrow(() -> new NullPointerException("there is no repository which have this id"));

		List<Release> releaseList = releaseRepository.findByPublishedAtBetween(startDateTime, endDateTime);

		List<FailedChange> failedChangeList = failedChangeRepository.findByRepositoryAndFailedAtBetween
			(repository, startDateTime, endDateTime);
		Map<LocalDate, Double> changeFailureRateMap = makeChangeFailureRateMap(releaseList, failedChangeList);

		return new ChangeFailureRateDto(startTime, endTime, changeFailureRateMap);
	}

	private Map<LocalDate, Double> makeChangeFailureRateMap(List<Release> releaseList,
		List<FailedChange> failedChangeList) {
		Map<LocalDate, Double> releaseCountDoubleTypeMap = makeReleaseCountDoubleTypeMap(releaseList);
		Map<LocalDate, Double> changeFailureCountDoubleTypeMap = makeChangeFailureCountDoubleTypeMap(failedChangeList);
		changeFailureCountDoubleTypeMap.forEach((date, changeFailureCount)
			-> releaseCountDoubleTypeMap.merge(date, changeFailureCount,
			(changeFailureCountForMerge, releaseCountForMerge) -> changeFailureCountForMerge / releaseCountForMerge));
		return changeFailureCountDoubleTypeMap;
	}

	private Map<LocalDate, Double> makeChangeFailureCountDoubleTypeMap(List<FailedChange> failedChangeList) {
		Map<LocalDate, Integer> changeFailureCountMap = getChangeFailureCountMap(failedChangeList);
		return makeIntegerTypeMapToDoubleTypeMap(changeFailureCountMap);
	}

	private Map<LocalDate, Double> makeReleaseCountDoubleTypeMap(List<Release> releaseList) {
		Map<LocalDate, Integer> releaseCountMap = makeReleaseCountMap(releaseList);
		return makeIntegerTypeMapToDoubleTypeMap(releaseCountMap);
	}

	private static Map<LocalDate, Double> makeIntegerTypeMapToDoubleTypeMap(Map<LocalDate, Integer> changeFailureCountMap) {
		return changeFailureCountMap.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey, t -> t.getValue().doubleValue()));
	}

	private Map<LocalDate, Integer> makeReleaseCountMap(List<Release> releaseList) {
		Map<LocalDate, Integer> releaseCountMap = new HashMap<>();
		releaseList.forEach(release -> {
				LocalDate publishedDate = release.getPublishedAt().toLocalDate();
				releaseCountMap.put(publishedDate, releaseCountMap.getOrDefault(publishedDate, 0) + 1);});
		return releaseCountMap;
	}

	private Map<LocalDate, Integer> getChangeFailureCountMap(List<FailedChange> failedChangeList) {
		Map<LocalDate, List<Integer>> changeFailureListMap = makeChangeFailureListMap(failedChangeList);
		return makeChangeFailureCountMap(changeFailureListMap);
	}

	private static Map<LocalDate, List<Integer>> makeChangeFailureListMap(List<FailedChange> failedChangeList) {
		return failedChangeList.stream()
			.map(failedChange -> new AbstractMap.SimpleEntry<LocalDate, List<Integer>>(
				failedChange.getFailedAt().toLocalDate(), new ArrayList<>()))
			.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
	}

	private static Map<LocalDate, Integer> makeChangeFailureCountMap(
		Map<LocalDate, List<Integer>> changeFailureListMap) {
		return changeFailureListMap.entrySet().stream()
			.map(changeFailureCountEntry -> new AbstractMap.SimpleEntry<LocalDate, Integer>(
				changeFailureCountEntry.getKey(),
				changeFailureCountEntry.getValue()
					.stream()
					.mapToInt(changeFailureCount -> changeFailureCount)
					.sum()))
			.collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
	}
}
