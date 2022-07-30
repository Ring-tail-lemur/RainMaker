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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Repository {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repository_id")
	private Long id;

	@OneToMany(mappedBy = "repository")
	private List<Branch> branchList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "repository")
	private PullRequest PullRequest;

	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_owner_table_id")
	private RepositoryOwnerTable repositoryOwnerTable;
}
