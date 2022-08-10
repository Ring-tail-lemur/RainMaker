package com.ringtaillemur.analyst.domain;

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

	@OneToMany(mappedBy = "issue")
	private List<IssueLabel> issueLabelList = new ArrayList<>();

	@OneToMany(mappedBy = "issue")
	private List<IssueEvent> issueEventList = new ArrayList<>();
}
