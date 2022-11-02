package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.PullRequest;
import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetail;
import com.ringtaillemur.rainmaker.dto.entitydto.LeadTimeForChangeSourcePullRequestDetailDataDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.CycleTimeDetailDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.CycleTimeDetailsBySourcePrDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeDetailDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.service.LeadTimeForChangeSourcePulRequestDetailService;
import com.ringtaillemur.rainmaker.service.PullRequestService;
import com.ringtaillemur.rainmaker.service.UtilService;
import com.ringtaillemur.rainmaker.util.exception.ConditionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeadTimeForChangeService {

	private final LeadTimeForChangeRepository leadTimeForChangeRepository;
	private final UtilService utilService;

	private final LeadTimeForChangeSourcePulRequestDetailService leadTimeForChangeSourcePulRequestDetailService;
	private final PullRequestService pullRequestService;

	public LeadTimeForChangeByTimeDto getLeadTimeForChangeByTime(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		List<LeadTimeForChange> leadTimeForChangeList = getTargetLeadTimeForChangeList(
			repositoryIds, startTime, endTime);

		Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap = getLocalDateLeadTimeForChangeDetailDtoMap(
			leadTimeForChangeList);

		return new LeadTimeForChangeByTimeDto(startTime, endTime, leadTimeForChangeDetailMap);
	}

	private Map<LocalDate, LeadTimeForChangeDetailDto> getLocalDateLeadTimeForChangeDetailDtoMap(
		List<LeadTimeForChange> leadTimeForChangeList) {

		Function<LeadTimeForChange, LocalDate> getDeploymentTime =
			leadTimeForChange -> leadTimeForChange.getDeploymentTime().toLocalDate();

		Map<LocalDate, Double> codingTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			getDeploymentTime, LeadTimeForChange::getCodingTimePart);

		Map<LocalDate, Double> pickupTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			getDeploymentTime, LeadTimeForChange::getPickupTimePart);

		Map<LocalDate, Double> reviewTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			getDeploymentTime, LeadTimeForChange::getReviewTimePart);

		Map<LocalDate, Double> deployTimeMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
			getDeploymentTime, LeadTimeForChange::getDeploymentTimePart);

		Map<LocalDate, LeadTimeForChangeDetailDto> leadTimeForChangeDetailMap = new HashMap<>();

		for (LocalDate localDate : codingTimeMap.keySet()) {
			LeadTimeForChangeDetailDto leadTimeForChangeDetailDto = LeadTimeForChangeDetailDto.builder()
				.codingTime(codingTimeMap.get(localDate))
				.pickupTime(pickupTimeMap.get(localDate))
				.reviewTime(reviewTimeMap.get(localDate))
				.deployTime(deployTimeMap.get(localDate))
				.build();
			leadTimeForChangeDetailMap.put(localDate, leadTimeForChangeDetailDto);
		}

		return leadTimeForChangeDetailMap;
	}

	public CycleTimeDetailDto getCycleTimeDetailDto(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		List<LeadTimeForChange> leadTimeForChangeList = getTargetLeadTimeForChangeList(
			repositoryIds, startTime, endTime);
		return new CycleTimeDetailDto(
			leadTimeForChangeList.stream()
				.map(LeadTimeForChangeDetailDto::new)
				.collect(Collectors.toList())
		);
	}

	public List<CycleTimeDetailsBySourcePrDto> getCycleTimeDetailsBySourcePrDto(List<Long> repositoryIds,
		LocalDate startTime, LocalDate endTime) {
		List<LeadTimeForChange> leadTimeForChangeList =
			getTargetLeadTimeForChangeList(repositoryIds, startTime, endTime);

		List<LeadTimeForChangeSourcePullRequestDetail> sourcePullRequestDetailList =
			leadTimeForChangeSourcePulRequestDetailService.findByLeadTimeForChangeIdList(leadTimeForChangeList);

		List<LeadTimeForChangeSourcePullRequestDetailDataDto> sourcePullRequestDetailDataDtoList =
			sourcePullRequestDetailList.stream().
				map(LeadTimeForChangeSourcePullRequestDetailDataDto::new)
				.toList();

		setBranchStayDurations(sourcePullRequestDetailDataDtoList);
		return sourcePullRequestDetailDataDtoList.stream()
			.map(CycleTimeDetailsBySourcePrDto::new)
			.toList();
	}

	private void setBranchStayDurations(
		List<LeadTimeForChangeSourcePullRequestDetailDataDto> sourcePullRequestDetailDataDtoList) {
		sourcePullRequestDetailDataDtoList.forEach(sourcePullRequestDetailDataDto -> {
			Map<String, Long> stayBranchNameAndDuration =
				getStayBranchNameAndDuration(sourcePullRequestDetailDataDto.getVisitedPullRequests(),
					sourcePullRequestDetailDataDto.getCodingTime());
			sourcePullRequestDetailDataDto.setBranchStayDuration(stayBranchNameAndDuration);
		});
	}

	private Map<String, Long> getStayBranchNameAndDuration(String visitedPullRequest, Long codingTime) {
		Map<String, LocalDateTime> branchNameAndPullRequestCloseTime =
			getBranchNameAndPullRequestCloseTime(visitedPullRequest, codingTime);
		return convertPrCloseTimeToStayDuration(branchNameAndPullRequestCloseTime);
	}

	private Map<String, Long> convertPrCloseTimeToStayDuration(
		Map<String, LocalDateTime> branchNameAndPullRequestCloseTime) {
		Map<String, Long> stayBranchNameAndDuration = new LinkedHashMap<>();
		String lastBranchName = "";
		for (String currentBranchName : branchNameAndPullRequestCloseTime.keySet()) {
			if (lastBranchName.isEmpty()) {
				lastBranchName = currentBranchName;
				continue;
			}
			LocalDateTime lastPrCloseTime = branchNameAndPullRequestCloseTime.get(lastBranchName);
			LocalDateTime currentPrCloseTime = branchNameAndPullRequestCloseTime.get(currentBranchName);
			Long stayDuration = Duration.between(lastPrCloseTime, currentPrCloseTime).toMinutes();
			stayBranchNameAndDuration.put(lastBranchName, stayDuration);
			lastBranchName = currentBranchName;
		}
		stayBranchNameAndDuration.put(lastBranchName, -1L);
		return stayBranchNameAndDuration;
	}

	private Map<String, LocalDateTime> getBranchNameAndPullRequestCloseTime(String visitedPullRequest,
		Long codingTime) {
		HashMap<String, LocalDateTime> branchStayEndTimeMap = new LinkedHashMap<>();
		List<String> visitedPullRequestList = Arrays.asList(visitedPullRequest.split(">"));
		for (String pullRequestId : visitedPullRequestList) {
			PullRequest pullRequest = pullRequestService.findById(Long.valueOf(pullRequestId));
			LocalDateTime pullRequestCloseTime = pullRequest.getPullRequestEventList()
				.stream()
				.filter(pullRequestEvent -> pullRequestEvent.getPullRequestEventType().equals("CLOSED"))
				.findAny().orElseThrow(() -> new ConditionNotFoundException("CLOSED되지 않은 pr입니다."))
				.getEventTime();
			if (visitedPullRequestList.get(0).equals(pullRequestId)) {
				String openBranchName = pullRequest.getPullRequestOpenBranchName();
				branchStayEndTimeMap.put(openBranchName, pullRequestCloseTime.minusMinutes(codingTime));
			}
			String closeBranchName = pullRequest.getPullRequestCloseBranchName();
			branchStayEndTimeMap.put(closeBranchName, pullRequestCloseTime);
		}
		return branchStayEndTimeMap;
	}

	private List<LeadTimeForChange> getTargetLeadTimeForChangeList(List<Long> repositoryIds, LocalDate startTime,
		LocalDate endTime) {
		LocalDateTime startDateTime = startTime.atStartOfDay();
		LocalDateTime endDateTime = endTime.plusDays(1).atStartOfDay();
		return leadTimeForChangeRepository.findByRepositoryIdInAndDeploymentTimeBetween(
			repositoryIds, startDateTime, endDateTime);
	}
}
