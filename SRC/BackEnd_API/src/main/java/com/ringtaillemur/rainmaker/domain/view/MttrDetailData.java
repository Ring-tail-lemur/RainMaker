package com.ringtaillemur.rainmaker.domain.view;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MttrDetailData implements Serializable {

	private static final long serialVersionUID = -2997257809208169414L;
	private LocalDateTime findTime;
	private LocalDateTime firstCommitTime;
	private LocalDateTime firstReviewTime;
	private LocalDateTime prOpenTime;
	private LocalDateTime prCloseTime;
	private LocalDateTime deploymentTime;
	private LocalDateTime endTime;
	private Long issueNumber;
	private String issueUrl;
	private Long repositoryId;
}
