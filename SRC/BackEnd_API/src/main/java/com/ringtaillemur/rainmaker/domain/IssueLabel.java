package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@OneToMany(mappedBy = "issueLabel")
	private List<Issue> issueList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "issueLabel")
	private DeploymentWorkflow deploymentWorkflow;
}
