package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.TimeToRestoreService;

public interface TimeToRestoreServiceRepository extends JpaRepository<TimeToRestoreService, Long> {
}
