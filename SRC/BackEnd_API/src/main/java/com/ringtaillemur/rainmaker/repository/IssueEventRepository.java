package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.IssueEvent;

public interface IssueEventRepository extends JpaRepository<IssueEvent, Long> {
}
