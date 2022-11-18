package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.ReleaseSuccess;
import com.ringtaillemur.rainmaker.domain.procedure.ChangeFailureRateDetail;
import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDetailDto;
import com.ringtaillemur.rainmaker.repository.ChangeFailureRateDetailRepository;
import com.ringtaillemur.rainmaker.repository.ReleaseSuccessRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeFailureRateService {

	private final ReleaseSuccessRepository releaseSuccessRepository;
	private final ChangeFailureRateDetailRepository changeFailureRateDetailRepository;
	private final UtilService utilService;

	public ChangeFailureRateDto getChangeFailureRate(List<Long> repositoryIds, LocalDate startTime, LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ReleaseSuccess> releaseSuccessList = releaseSuccessRepository.findByRepositoryIdInAndReleasedAtBetween
			(repositoryIds, startDateTime, endDateTime);

		Map<LocalDate, Double> changeFailureRateMap = utilService.makeDailyAverageMap(releaseSuccessList,
			releaseSuccess -> releaseSuccess.getReleasedAt().toLocalDate(),
			ReleaseSuccess::getIsSuccessLongValue);

		return new ChangeFailureRateDto(startTime, endTime, changeFailureRateMap);
	}

	public List<ChangeFailureRateDetailDto> getChangeFailureRateDetailDto(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ChangeFailureRateDetail> changeFailureRateDetails = changeFailureRateDetailRepository.changeFailureRateProcedure(
			repositoryIds.toString(),
			startDateTime, endDateTime);

		return getDeploymentFrequencyDetailDtos(changeFailureRateDetails);
	}

	private List<ChangeFailureRateDetailDto> getDeploymentFrequencyDetailDtos(
		List<ChangeFailureRateDetail> changeFailureRateDetails) {
		if(changeFailureRateDetails.isEmpty())
			return null;

		List<List<ChangeFailureRateDetail>> changeFailureRateDetailPairs = IntStream
			.range(0, changeFailureRateDetails.size()-1)
			.mapToObj(index -> Arrays.asList(changeFailureRateDetails.get(index), changeFailureRateDetails.get(index + 1)))
			.collect(Collectors.toList());

		ChangeFailureRateDetail lastChangeFailureRateDetail = changeFailureRateDetails.get(changeFailureRateDetails.size() - 1);
		changeFailureRateDetailPairs.add(List.of(lastChangeFailureRateDetail, lastChangeFailureRateDetail));

		return changeFailureRateDetailPairs.stream()
			.map(ReleaseDetailPair -> {
				List<LocalDateTime> dateTimes = ReleaseDetailPair.stream()
					.map(ChangeFailureRateDetail::getPublishedAt).toList();
				int days = Period.between(dateTimes.get(0).toLocalDate(), dateTimes.get(1).toLocalDate()).getDays();
				ChangeFailureRateDetail changeFailureRateDetail = ReleaseDetailPair.get(0);
				return new ChangeFailureRateDetailDto(changeFailureRateDetail, days);
			}).toList();
	}
}
