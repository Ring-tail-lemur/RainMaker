package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.Map;

import com.ringtaillemur.rainmaker.dto.entitydto.LeadTimeForChangeSourcePullRequestDetailDataDto;

import lombok.Data;

@Data
public class CycleTimeDetailsBySourcePrDto {
	private String pullRequestUrl;

	private CycleTimeDetailDto cycleTimeDetailDto;

	private Long codeChange;
	private Long reviewSize;

	private Map<String, Long> branchStayDuration;

	public CycleTimeDetailsBySourcePrDto(
		LeadTimeForChangeSourcePullRequestDetailDataDto sourcePullRequestDetailDataDto) {
		pullRequestUrl = sourcePullRequestDetailDataDto.getPullRequestUrl();
		codeChange = sourcePullRequestDetailDataDto.getCodeChange();
		reviewSize = sourcePullRequestDetailDataDto.getReviewSize();
		cycleTimeDetailDto = CycleTimeDetailDto.builder()
			.codingAverageTime(sourcePullRequestDetailDataDto.getCodingTime())
			.pickupAverageTime(sourcePullRequestDetailDataDto.getPickupTime())
			.reviewAverageTime(sourcePullRequestDetailDataDto.getReviewTime())
			.deployAverageTime(sourcePullRequestDetailDataDto.getDeployTime())
			.build();
		branchStayDuration = sourcePullRequestDetailDataDto.getBranchStayDuration();
	}
}
