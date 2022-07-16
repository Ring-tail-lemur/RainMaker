package com.rainmaker.rainmakerwebserver.domain.generic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.generic.entity.GitRepository;

public interface GitRepositoryRepository extends JpaRepository<GitRepository, Long> {
}
