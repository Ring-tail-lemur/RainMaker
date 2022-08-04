package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.PullRequest;
import com.ringtaillemur.rainmaker.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {
}
