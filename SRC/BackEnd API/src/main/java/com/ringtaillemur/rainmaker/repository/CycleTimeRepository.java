package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;

public interface CycleTimeRepository extends JpaRepository<LeadTimeForChange, Long> {

	List<LeadTimeForChange> findCycleTimesByCycleTimeEndBetween(LocalDateTime measurementStartTime,
		LocalDateTime measurementEndTime);
}
