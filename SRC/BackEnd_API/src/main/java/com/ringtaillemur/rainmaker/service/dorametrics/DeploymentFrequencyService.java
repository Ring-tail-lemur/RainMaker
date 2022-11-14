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
import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.repository.ReleaseDetailRepository;
import com.ringtaillemur.rainmaker.repository.ReleaseSuccessRepository;
import com.ringtaillemur.rainmaker.service.UtilService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeploymentFrequencyService {

	private final ReleaseSuccessRepository releaseSuccessRepository;
	private final UtilService utilService;
	private final ReleaseDetailRepository releaseDetailRepository;

	public DeploymentFrequencyDto getDeploymentFrequency(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ReleaseSuccess> releaseList = releaseSuccessRepository.findByRepositoryIdInAndReleasedAtBetween(
			repositoryIds,
			startDateTime, endDateTime);

		Map<LocalDate, Integer> dailyCountMap = utilService.makeDailyCountMap(releaseList,
			releaseSuccess -> releaseSuccess.getReleasedAt().toLocalDate());

		return new DeploymentFrequencyDto(startTime, endTime, dailyCountMap);
	}

	public List<DeploymentFrequencyDetailDto> getDeploymentFrequencyDetailDto(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();

		List<ReleaseDetail> releaseDetails = releaseDetailRepository.releaseDetailProcedure(repositoryIds.toString(),
			startDateTime, endDateTime);

		return getDeploymentFrequencyDetailDtos(releaseDetails);
	}

	private List<DeploymentFrequencyDetailDto> getDeploymentFrequencyDetailDtos(
		List<ReleaseDetail> releaseDetails) {
		if(releaseDetails.isEmpty())
			return null;

		List<List<ReleaseDetail>> releaseDetailPairs = IntStream
			.range(0, releaseDetails.size()-1)
			.mapToObj(index -> Arrays.asList(releaseDetails.get(index), releaseDetails.get(index + 1)))
			.collect(Collectors.toList());

		ReleaseDetail lastReleaseDetail = releaseDetails.get(releaseDetails.size() - 1);
		releaseDetailPairs.add(List.of(lastReleaseDetail, lastReleaseDetail));

		return releaseDetailPairs.stream()
			.map(ReleaseDetailPair -> {
				List<LocalDateTime> dateTimes = ReleaseDetailPair.stream()
					.map(ReleaseDetail::getPublishedAt).toList();
				int days = Period.between(dateTimes.get(0).toLocalDate(), dateTimes.get(1).toLocalDate()).getDays();
				ReleaseDetail releaseDetail = ReleaseDetailPair.get(0);
				return new DeploymentFrequencyDetailDto(releaseDetail, days);
			}).toList();
	}

}
