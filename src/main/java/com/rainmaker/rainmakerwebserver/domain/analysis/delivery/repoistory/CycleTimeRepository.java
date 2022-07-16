package com.rainmaker.rainmakerwebserver.domain.analysis.delivery.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.analysis.delivery.entity.CycleTime;

public interface CycleTimeRepository extends JpaRepository<CycleTime, Long> {
}
