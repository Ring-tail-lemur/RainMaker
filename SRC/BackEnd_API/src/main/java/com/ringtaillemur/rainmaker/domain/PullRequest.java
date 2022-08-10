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
public class PullRequest extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pull_request_id")
	private Long id;

	private Long remote_identifier;

	private Long pull_request_number;

	private Boolean process_end;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_open_branch_id")
	private Branch pullRequestOpenBranch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_close_branch_id")
	private Branch pullRequestCloseBranch;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pullRequest")
	private LeadTimeForChange leadTimeForChange;

	@OneToMany(mappedBy = "sourcePullRequest")
	private List<PullRequestDirection> outgoingPullRequestList = new ArrayList<>();

	@OneToMany(mappedBy = "outgoingPullRequest")
	private List<PullRequestDirection> sourcePullRequestList = new ArrayList<>();

	@OneToMany(mappedBy = "pullRequest")
	private List<PullRequestComment> pullRequestCommentList = new ArrayList<>();

	@OneToMany(mappedBy = "pullRequest")
	private List<PullRequestCommitTable> pullRequestCommitTableList = new ArrayList<>();

	@OneToMany(mappedBy = "pullRequest")
	private List<PullRequestEvent> pullRequestEventList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "pullRequest")
	private WorkflowRun workflowRun;
}
