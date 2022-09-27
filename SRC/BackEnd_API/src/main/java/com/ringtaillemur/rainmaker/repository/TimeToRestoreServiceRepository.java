package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.TimeToRestoreService;

public interface TimeToRestoreServiceRepository extends JpaRepository<TimeToRestoreService, Long> {

	List<TimeToRestoreService> findByRepositoryIdAndRestoredAtBetween(Long repositoryId, LocalDateTime startTime,
		LocalDateTime endTime);

	List<TimeToRestoreService> findByRepositoryIdInAndRestoredAtBetween(List<Long> repositoryIds, LocalDateTime startTime,
																	  LocalDateTime endTime);
}
