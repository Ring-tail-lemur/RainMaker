package com.ringtaillemur.rainmaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.procedure.ChangeFailureRateDetail;
import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

public interface ChangeFailureRateDetailRepository extends JpaRepository<ChangeFailureRateDetail, Long> {
	@Procedure(name = "getChangeFailureRateDetail")
	List<ChangeFailureRateDetail> changeFailureRateProcedure(@Param("repoString") String repoString,
		@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
