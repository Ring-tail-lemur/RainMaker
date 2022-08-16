package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.ringtaillemur.rainmaker.domain.enumtype.IssueState;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "issue_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "open_user_id")
	private GitUser openUser;

	@Enumerated(EnumType.STRING)
	private IssueState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_label_id")
	private IssueLabel issueLabel;

	@OneToMany(mappedBy = "issue")
	private List<IssueEvent> issueEventList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "firstErrorIssue")
	private FailedChange failedChange;

	private Long remoteIdentifier;
}
