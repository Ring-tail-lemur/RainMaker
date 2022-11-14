package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

public interface ReleaseDetailRepository extends JpaRepository<ReleaseDetail, Long> {
	@Procedure(name = "getReleaseDetailList")
	List<ReleaseDetail> releaseDetailProcedure(@Param("repoString") String repoString,
		@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
