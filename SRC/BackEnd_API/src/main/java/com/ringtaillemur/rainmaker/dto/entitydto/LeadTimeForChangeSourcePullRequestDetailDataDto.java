package com.ringtaillemur.rainmaker.dto.entitydto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetail;
import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetailData;

import lombok.Data;

@Data
public class LeadTimeForChangeSourcePullRequestDetailDataDto implements Serializable {
	private final Long pullRequestId;
	private final Long leadTimeForChangeId;
	private final Long codingTime;
	private final Long pickupTime;
	private final Long reviewTime;
	private final Long deployTime;
	private final Long codeChange;
	private final Long reviewSize;
	private final String visitedPullRequests;
	private final String pullRequestUrl;

	private Map<String, Long> branchStayDuration = new HashMap<>();

	public LeadTimeForChangeSourcePullRequestDetailDataDto(
		LeadTimeForChangeSourcePullRequestDetail sourcePullRequestDetail) {
		LeadTimeForChangeSourcePullRequestDetailData data = sourcePullRequestDetail.getData();
		pullRequestId = data.getPullRequestId();
		leadTimeForChangeId = data.getLeadTimeForChangeId();
		codingTime = data.getCodingTime();
		pickupTime = data.getPickupTime();
		reviewTime = data.getReviewTime();
		deployTime = data.getDeployTime();
		codeChange = data.getCodeChange();
		reviewSize = data.getReviewSize();
		visitedPullRequests = data.getVisitedPullRequests();
		pullRequestUrl = data.getPullRequestUrl();
	}
}
