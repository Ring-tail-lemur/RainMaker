package com.rainmaker.rainmakerwebserver.domain.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.meta.entity.GitRepository;

public interface GitRepositoryRepository extends JpaRepository<GitRepository, Long> {
}
