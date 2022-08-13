package com.ringtaillemur.analyst.domain;

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
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
public class GitOrganization extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "git_organization_id")
	private Long id;

	private String name;

	private Long remoteIdentifier;

	@OneToMany(mappedBy = "gitOrganization")
	private List<UserOrganizationTable> userOrganizationTableList = new ArrayList<>();

	@OneToMany(mappedBy = "ownerOrganization")
	private List<Repository> RepositoryList = new ArrayList<>();
}
