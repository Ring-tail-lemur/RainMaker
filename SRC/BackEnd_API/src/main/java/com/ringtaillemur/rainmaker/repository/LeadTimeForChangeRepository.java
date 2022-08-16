package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.Repository;

public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChange, Long> {

    @Query("select l " +
            "from LeadTimeForChange l " +
            "where l.repository = :repo " +
            "and l.modifiedDate > :start_time " +
            "and l.modifiedDate < :end_time")
    List<LeadTimeForChange> findByRepoIdAndTime(@Param("repo") Repository repository, @Param("start_time") LocalDateTime start_time, @Param("end_time") LocalDateTime end_time);
}
