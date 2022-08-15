package com.ringtaillemur.analyst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeploymentWorkflow extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deployment_workflow_id")
	private Long id;

	private String name;
	private Long remoteIdentifier;
	private String path;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deploymentWorkflow")
	private WorkflowRun workflowRun;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_label_id")
	private IssueLabel issueLabel;
}
