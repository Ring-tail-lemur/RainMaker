package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.Repository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
	List<Repository> findByOwnerOrganizationId(Long ownerOrganizationId);

	@Query("SELECT r FROM Repository r WHERE r.id in :repositories")
	List<Repository> findByIdsIn(@Param("repositories") List<Long> repositories);
}
