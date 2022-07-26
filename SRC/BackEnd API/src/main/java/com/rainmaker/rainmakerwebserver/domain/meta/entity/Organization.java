package com.rainmaker.rainmakerwebserver.domain.meta.entity;

import com.rainmaker.rainmakerwebserver.domain.BaseEntity;
import com.rainmaker.rainmakerwebserver.domain.enumtype.RemoteRepositoryType;
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
