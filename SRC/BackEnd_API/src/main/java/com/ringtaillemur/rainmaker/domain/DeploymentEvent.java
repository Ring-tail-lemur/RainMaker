package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeploymentEvent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deployment_event_id")
	private Long id;

	private Long remoteIdentifier;

	private LocalDateTime deploymentSuccessTime;

	private Boolean processEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deploymentEvent")
	private LeadTimeForChange leadTimeForChange;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;
}
