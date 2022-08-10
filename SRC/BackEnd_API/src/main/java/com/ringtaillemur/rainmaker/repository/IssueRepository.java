package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
