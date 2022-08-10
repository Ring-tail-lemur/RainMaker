package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.IssueLabel;

public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
}
