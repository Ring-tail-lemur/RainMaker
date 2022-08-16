package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.ReleaseEvent;

public interface ReleaseEventRepository extends JpaRepository<ReleaseEvent, Long> {
}
