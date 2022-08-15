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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Repository extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repository_id")
	private Long id;

	@OneToMany(mappedBy = "repository")
	private List<Branch> branchList = new ArrayList<>();

	@OneToMany(mappedBy = "repository")
	private List<PullRequest> pullRequestList = new ArrayList<>();

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_organization_id")
	private GitOrganization ownerOrganization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_user_id")
	private GitUser ownerUser;

	@OneToMany(mappedBy = "repository")
	private List<LeadTimeForChange> leadTimeForChangeList = new ArrayList<>();

	@OneToMany(mappedBy = "repository")
	private List<Issue> issueList = new ArrayList<>();

	@OneToMany(mappedBy = "repository")
	private List<WorkflowRun> workflowRuns = new ArrayList<>();

	@OneToMany(mappedBy = "repository")
	private List<Release> releaseList = new ArrayList<>();

	@OneToMany(mappedBy = "repository")
	private List<FailedChange> failedChangeList = new ArrayList<>();
}
