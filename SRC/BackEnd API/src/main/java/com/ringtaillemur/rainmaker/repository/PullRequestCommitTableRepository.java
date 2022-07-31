package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;

public interface PullRequestCommitTableRepository extends JpaRepository<GitOrganization, Long> {
}
