package com.ringtaillemur.rainmaker.domain.view;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class CodeVisitBranchesData implements Serializable {

	private Long sourcePullRequestId;
	private Long outgoingPullRequestId;
	private LocalDateTime movedTime;
	private String startBranch;
	private String middleBranch;
	private String endBranch;

}
