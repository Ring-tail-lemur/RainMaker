package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.Repository;

import java.util.List;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    List<Repository> findByOwnerOrganizationId(Long ownerOrganizationId);
}