package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.ReleaseSuccess;

public interface ReleaseSuccessRepository extends JpaRepository<ReleaseSuccess, Long> {

	List<ReleaseSuccess> findByRepositoryIdAndReleasedAtBetween(Long repositoryId, LocalDateTime startTime,
		LocalDateTime endTime);

	List<ReleaseSuccess> findByRepositoryIdInAndReleasedAtBetween(List<Long> repositoryIds, LocalDateTime startTime,
																LocalDateTime endTime);
}
