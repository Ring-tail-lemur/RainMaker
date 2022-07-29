package com.ringtaillemur.rainmaker.domain.jpadomain.generic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ringtaillemur.rainmaker.domain.jpadomain.BaseEntity;
import com.ringtaillemur.rainmaker.domain.jpadomain.analysis.delivery.CycleTime;
import com.ringtaillemur.rainmaker.util.enumtype.RemoteRepositoryType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Organization extends BaseEntity {
	@Id
	@GeneratedValue
	private String id;

	private String name;

	@Enumerated(EnumType.STRING)
	private RemoteRepositoryType remoteRepositoryType;

	@OneToMany(mappedBy = "organization")
	private List<CycleTime> cycleTimeList = new ArrayList<>();

	@OneToMany(mappedBy = "organization")
	private List<GitRepository> gitRepositoryList = new ArrayList<>();

	@Builder
	public Organization(String name, RemoteRepositoryType remoteRepositoryType) {
		this.name = name;
		this.remoteRepositoryType = remoteRepositoryType;
	}

	public void addCycleTime(CycleTime cycleTime) {
		cycleTime.setOrganization(this);
	}

	public void addGitRepository(GitRepository gitRepository) {
		gitRepository.setOrganization(this);
	}
}
