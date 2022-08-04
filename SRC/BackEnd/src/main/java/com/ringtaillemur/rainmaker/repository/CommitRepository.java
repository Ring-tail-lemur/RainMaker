package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;

public interface CommitRepository extends JpaRepository<GitOrganization, Long> {
}
