package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.Map;

import com.ringtaillemur.rainmaker.dto.entitydto.LeadTimeForChangeSourcePullRequestDetailDataDto;

import lombok.Builder;
import lombok.Data;

@Data
public class CycleTimeDetailsBySourcePrDto {
	private String pullRequestUrl;

	private CycleTimeDetailDto cycleTimeDetailDto;

	private Long codeChange;
	private Long reviewSize;

	private Map<String, Long> branchStayDuration;

	@Builder
	public CycleTimeDetailsBySourcePrDto(String pullRequestUrl, CycleTimeDetailDto cycleTimeDetailDto, Long codeChange,
		Long reviewSize, Map<String, Long> branchStayDuration) {
		this.pullRequestUrl = pullRequestUrl;
		this.cycleTimeDetailDto = cycleTimeDetailDto;
		this.codeChange = codeChange;
		this.reviewSize = reviewSize;
		this.branchStayDuration = branchStayDuration;
	}

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
