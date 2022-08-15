package com.ringtaillemur.rainmaker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.FailedChange;

public interface FailedChangeRepository extends JpaRepository<FailedChange, Long> {

	Optional<FailedChange> findByRepositoryAndBetween()
}
