package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.jpadomain.analysis.delivery.CycleTime;

public interface CycleTimeRepository extends JpaRepository<CycleTime, Long> {

	List<CycleTime> findCycleTimesByCycleTimeEndBetween(LocalDateTime measurementStartTime,
		LocalDateTime measurementEndTime);
}
