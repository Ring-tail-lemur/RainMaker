package com.ringtaillemur.rainmaker.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeadTimeForChange extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "lead_time_for_change_id")
	private Long id;

	private LocalDateTime firstCommitTime;

	private LocalDateTime prOpenTime;

	private LocalDateTime firstReviewTime;

	private LocalDateTime prCloseTime;

	private LocalDateTime deploymentTime;

	private Long pullRequestId;

	private Long repositoryId;

	private Long releaseId;

	public Long getLeadTimeForChange() {
		return Duration.between(firstCommitTime, deploymentTime).toMinutes();
	}

	public Long getCodingTimePart() {
		return Duration.between(firstCommitTime, prOpenTime).toMinutes();
	}

	public Long getPickupTimePart() {
		return Duration.between(prOpenTime, firstReviewTime).toMinutes();
	}

	public Long getReviewTimePart() {
		return Duration.between(firstReviewTime, prCloseTime).toMinutes();
	}

	public Long getDeploymentTimePart() {
		return Duration.between(prCloseTime, deploymentTime).toMinutes();
	}
}
