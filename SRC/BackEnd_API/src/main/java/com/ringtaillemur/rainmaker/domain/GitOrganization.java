package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GitOrganization extends BaseEntity {
	@Id
	@Column(name = "git_organization_id")
	private Long id;

	private String name;

	@Builder
	public GitOrganization(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
