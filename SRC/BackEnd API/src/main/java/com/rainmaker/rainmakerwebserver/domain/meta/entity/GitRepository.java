package com.rainmaker.rainmakerwebserver.domain.meta.entity;

import com.rainmaker.rainmakerwebserver.domain.BaseEntity;
import com.rainmaker.rainmakerwebserver.domain.analysis.delivery.entity.CycleTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GitRepository extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "gitRepository")
    private List<CycleTime> cycleTimeList = new ArrayList<>();

    @Builder
    public GitRepository(String name, Organization organization) {
        this.name = name;
        setOrganization(organization);
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
        organization.getGitRepositoryList().add(this);
    }

    public void addCycleTime(CycleTime cycleTime) {
        cycleTime.setGitRepository(this);
    }
}
