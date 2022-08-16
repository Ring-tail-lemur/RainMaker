package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.FailedChange;
import com.ringtaillemur.rainmaker.domain.Repository;

public interface FailedChangeRepository extends JpaRepository<FailedChange, Long> {

	List<FailedChange> findByRepositoryAndFailedAtBetween(Repository repository, LocalDateTime failedAt,
		LocalDateTime failedAt2);
}
