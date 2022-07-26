package com.ringtaillemur.rainmaker.domain.jpadomain.analysis.delivery;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ringtaillemur.rainmaker.domain.jpadomain.BaseEntity;
import com.ringtaillemur.rainmaker.domain.jpadomain.generic.GitRepository;
import com.ringtaillemur.rainmaker.domain.jpadomain.generic.Organization;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CycleTime extends BaseEntity {

	@GeneratedValue
	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "git_repository_id")
	private GitRepository gitRepository;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	private LocalDateTime cycleTimeStart;

	private LocalDateTime cycleTimeEnd;

	private Long codingTime;

	private Long pickupTime;

	private Long reviewTime;

	private Long deploymentTime;

	private Long commitId;

	@Builder
	public CycleTime(GitRepository gitRepository,
		Organization organization,
		LocalDateTime cycleTimeStart,
		LocalDateTime cycleTimeEnd,
		Long codingTime,
		Long pickupTime,
		Long reviewTime,
		Long deploymentTime,
		Long commitId) {
		setGitRepository(gitRepository);
		setOrganization(organization);
		this.cycleTimeStart = cycleTimeStart;
		this.cycleTimeEnd = cycleTimeEnd;
		this.codingTime = codingTime;
		this.pickupTime = pickupTime;
		this.reviewTime = reviewTime;
		this.deploymentTime = deploymentTime;
		this.commitId = commitId;
	}

	public void setGitRepository(GitRepository gitRepository) {
		this.gitRepository = gitRepository;
		gitRepository.getCycleTimeList().add(this);
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
		organization.getCycleTimeList().add(this);
	}
}
