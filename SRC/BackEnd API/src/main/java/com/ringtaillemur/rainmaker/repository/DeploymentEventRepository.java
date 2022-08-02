package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.DeploymentEvent;

public interface DeploymentEventRepository extends JpaRepository<DeploymentEvent, Long> {
}
