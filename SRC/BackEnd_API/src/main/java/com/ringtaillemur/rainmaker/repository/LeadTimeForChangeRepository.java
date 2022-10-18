package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;

public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChange, Long> {

	List<LeadTimeForChange> findByRepositoryIdAndDeploymentTimeBetween(Long repository, LocalDateTime startTime,
		LocalDateTime endTime);

	List<LeadTimeForChange> findByRepositoryIdInAndDeploymentTimeBetween(List<Long> repositories,
		LocalDateTime startTime,
		LocalDateTime endTime);
}
