package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.WorkflowRun;

public interface WorkflowRunRepository extends JpaRepository<WorkflowRun, Long> {
	@Query("select wr " +
		"from WorkflowRun wr " +
		"where wr.repository = :repo " +
		"and wr.workflowEndTime > :start_time " +
		"and wr.workflowEndTime < :end_time")
	List<WorkflowRun> findByRepoIdAndTime(@Param("repo") Repository repository,
		@Param("start_time") LocalDateTime start_time,
		@Param("end_time") LocalDateTime end_time);

}
