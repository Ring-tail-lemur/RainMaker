package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

	Integer countByPublishedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

	List<Release> findByPublishedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
