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
import javax.persistence.OneToOne;

import com.ringtaillemur.rainmaker.domain.enumtype.Conclusion;
import com.ringtaillemur.rainmaker.domain.enumtype.TriggerEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WorkflowRun extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "workflow_run_id")
	private Long id;

	private Long remoteIdentifier;

	private Long runNumber;

	@Enumerated(EnumType.STRING)
	private TriggerEvent triggerEvent;

	@Enumerated(EnumType.STRING)
	private Conclusion conclusion;

	private boolean processEnd;

	private LocalDateTime workflowEndTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deployment_workflow_id")
	private DeploymentWorkflow deploymentWorkflow;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;
}
