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
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id")
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "git_user_id")
	private GitUser gitUser;

	@OneToMany(mappedBy = "pullRequestOpenBranch")
	private List<PullRequest> pullRequestOpenList = new ArrayList<>();

	@OneToMany(mappedBy = "pullRequestCloseBranch")
	private List<PullRequest> pullRequestCloseList = new ArrayList<>();

}
