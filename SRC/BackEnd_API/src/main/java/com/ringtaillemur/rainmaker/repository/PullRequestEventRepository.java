package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.PullRequestEvent;

public interface PullRequestEventRepository extends JpaRepository<PullRequestEvent, Long> {
}
