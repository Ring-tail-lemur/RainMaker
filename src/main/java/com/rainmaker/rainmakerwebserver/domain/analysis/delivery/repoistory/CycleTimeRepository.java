package com.rainmaker.rainmakerwebserver.domain.analysis.delivery.repoistory;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.analysis.delivery.entity.CycleTime;

public interface CycleTimeRepository extends JpaRepository<CycleTime, Long> {

	List<CycleTime> findCycleTimesByCycleTimeEndBetween(LocalDateTime measurementStartTime,
		LocalDateTime measurementEndTime);
}
