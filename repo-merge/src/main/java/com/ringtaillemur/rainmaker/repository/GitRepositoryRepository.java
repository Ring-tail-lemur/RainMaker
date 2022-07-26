package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.jpadomain.generic.GitRepository;

public interface GitRepositoryRepository extends JpaRepository<GitRepository, Long> {
}
