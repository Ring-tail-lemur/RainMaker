package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeadTimeForChange extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "lead_time_for_change_id")
	private Long id;

	private Long totalTime;

	private Long codingTime;

	private Long pickupTime;

	private Long reviewTime;

	private Long deployTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;
}
