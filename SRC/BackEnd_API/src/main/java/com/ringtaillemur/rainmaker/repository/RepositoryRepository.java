package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.Repository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
	List<Repository> findByOwnerOrganizationId(Long ownerOrganizationId);
}
