package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PullRequestEvent extends BaseEntity{
	@Id
	@Column(name = "pull_request_event_id")
	private Long id;

	private String pullRequestEventType;
	private LocalDateTime eventTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;
	private Long eventSenderId;
}
