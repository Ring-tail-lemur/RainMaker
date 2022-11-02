package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PullRequest extends BaseEntity{

	@Id
	@Column(name = "pull_request_id")
	private Long id;
	private Long pullRequestNumber;
	private Long repositoryId;
	private String pullRequestOpenBranchName;
	private String pullRequestCloseBranchName;
	private Boolean leadTimeForChangeProcessEnd;
	private Long additions;
	private Long deletions;

	@OneToMany(mappedBy = "pullRequest")
	private List<PullRequestEvent> pullRequestEventList = new ArrayList<>();

}
