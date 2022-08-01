package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ringtaillemur.rainmaker.service.OrganizationService;
import com.ringtaillemur.rainmaker.util.enumtype.RemoteRepositoryType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GitOrganization extends BaseEntity {
	@Id
	@GeneratedValue
	private String id;

	private String name;

	private Long remoteIdentifier;

	@OneToMany(mappedBy = "gitOrganization")
	private List<UserOrganizationTable> userOrganizationTableList = new ArrayList<>();

	@OneToMany(mappedBy = "ownerOrganization")
	private List<GitOrganization> gitOrganizationList = new ArrayList<>();
}
