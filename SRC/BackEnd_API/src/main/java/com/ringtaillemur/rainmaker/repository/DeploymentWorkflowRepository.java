package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.DeploymentWorkflow;

public interface DeploymentWorkflowRepository extends JpaRepository<DeploymentWorkflow, Long> {
}
