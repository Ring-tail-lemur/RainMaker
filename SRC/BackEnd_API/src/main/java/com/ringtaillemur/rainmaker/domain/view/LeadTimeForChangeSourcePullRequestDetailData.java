package com.ringtaillemur.rainmaker.domain.view;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeadTimeForChangeSourcePullRequestDetailData implements Serializable {

	private Long pullRequestId;
	private Long repositoryId;
	private Long leadTimeForChangeId;
	private Long codingTime;
	private Long pickupTime;
	private Long reviewTime;
	private Long deployTime;
	private Long codeChange;
	private Long reviewSize;
	private String visitedPullRequests;
	private String pullRequestUrl;
}
