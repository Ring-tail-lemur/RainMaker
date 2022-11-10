package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.ringtaillemur.rainmaker.domain.procedure.ReleaseDetail;

public interface ReleaseDetailRepository extends JpaRepository<ReleaseDetail, Long> {

	@Query(value = "EXEC varcharToRepoIds :s", nativeQuery = true)
	List<res> varcharToRepoIdProcedure(@Param("s") String repoIdListToString);

	@Procedure(name = "getReleaseDetailList")
	// @Query(value = "EXEC releaseDetail :s", nativeQuery = true)
	List<ReleaseDetail> releaseDetailProcedure(@Param("repoString") String repoString);

	static interface res {
		Long getRelease_id();
		String getRelease_name();
		String getTag_name();
		int getCommit_size();
		String getRepository_name();
		int getCode_change_size();
	}
}
