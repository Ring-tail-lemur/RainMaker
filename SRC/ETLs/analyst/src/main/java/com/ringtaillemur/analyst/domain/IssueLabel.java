package com.ringtaillemur.analyst.domain;

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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssueLabel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "issue_label_id")
	private Long id;

	private String label;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "issueLabel")
	private DeploymentWorkflow deploymentWorkflow;
}
