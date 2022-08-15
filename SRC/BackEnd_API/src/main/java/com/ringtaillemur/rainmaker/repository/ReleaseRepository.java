package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
}
