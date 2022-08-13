package com.ringtaillemur.analyst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PullRequestCommitTable extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pull_request_commit_table_id")
	private Long id;

	private Boolean first_commit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pull_request_id")
	private PullRequest pullRequest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commit_id")
	private Commits commits;
}
