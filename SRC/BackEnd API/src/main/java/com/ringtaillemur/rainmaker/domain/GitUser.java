package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GitUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "git_user_id")
	private Long id;

	private String name;

	private Long remote_identifier;

	@OneToMany(mappedBy = "gitUser")
	private List<Branch> branchList = new ArrayList<>();

	@OneToMany(mappedBy = "author")
	private List<Commit> commitList = new ArrayList<>();

	@OneToMany(mappedBy = "gitUser")
	private List<PullRequestComment> pullRequestCommentList = new ArrayList<>();

	@OneToMany(mappedBy = "eventSender")
	private List<PullRequestEvent> pullRequestEventList = new ArrayList<>();

	@OneToMany(mappedBy = "ownerUser")
	private List<RepositoryOwnerTable> repositoryOwnerTableList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<UserOrganizationTable> userOrganizationTableList = new ArrayList<>();
}
