package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.DeploymentEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DeploymentEventRepository extends JpaRepository<DeploymentEvent, Long> {

    @Query("select D " +
            "from DeploymentEvent D " +
            "where D.repository = :repo " +
            "and D.deploymentSuccessTime > :start_time " +
            "and D.deploymentSuccessTime < :end_time")
    List<DeploymentEvent> findByRepoIdAndTime(@Param("repo") Repository repository,
                                                @Param("start_time") LocalDateTime start_time,
                                                @Param("end_time") LocalDateTime end_time );


}
