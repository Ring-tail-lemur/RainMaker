package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.PullRequest;

public interface PullRequestRepository extends JpaRepository<PullRequest, Long> {
}
