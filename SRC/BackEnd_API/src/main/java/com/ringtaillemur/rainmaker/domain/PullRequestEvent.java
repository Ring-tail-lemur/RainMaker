package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ringtaillemur.rainmaker.domain.enumtype.PullRequestEventType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PullRequestEvent extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pull_request_event_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private PullRequestEventType pullRequestEventType;

	private LocalDateTime eventTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_sender_id")
	private GitUser eventSender;
}
