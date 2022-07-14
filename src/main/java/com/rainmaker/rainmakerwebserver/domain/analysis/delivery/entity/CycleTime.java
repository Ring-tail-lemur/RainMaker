package com.rainmaker.rainmakerwebserver.domain.analysis.delivery.entity;

import com.rainmaker.rainmakerwebserver.domain.BaseEntity;
import com.rainmaker.rainmakerwebserver.domain.meta.entity.GitRepository;
import com.rainmaker.rainmakerwebserver.domain.meta.entity.Organization;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private Long fullCycleTime;

    private Long codingTime;

    private Long pickupTime;

    private Long reviewTime;

    private Long deploymentTime;

    @Builder
    public CycleTime(GitRepository gitRepository,
                     Organization organization,
                     LocalDateTime cycleTimeStart,
                     LocalDateTime cycleTimeEnd,
                     Long fullCycleTime,
                     Long codingTime,
                     Long pickupTime,
                     Long reviewTime,
                     Long deploymentTime) {
        setGitRepository(gitRepository);
        setOrganization(organization);
        this.cycleTimeStart = cycleTimeStart;
        this.cycleTimeEnd = cycleTimeEnd;
        this.fullCycleTime = fullCycleTime;
        this.codingTime = codingTime;
        this.pickupTime = pickupTime;
        this.reviewTime = reviewTime;
        this.deploymentTime = deploymentTime;
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
