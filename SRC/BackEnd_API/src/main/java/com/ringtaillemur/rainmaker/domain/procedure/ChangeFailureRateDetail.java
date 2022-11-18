package com.ringtaillemur.rainmaker.domain.procedure;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "ChangeFailureRateDetail")
@Getter
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "getChangeFailureRateDetail",
		procedureName = "changeFailureRateDetail",
		resultClasses = {ChangeFailureRateDetail.class},
		parameters = {
			@StoredProcedureParameter(
				name = "repoString",
				type = String.class,
				mode = ParameterMode.IN),
			@StoredProcedureParameter(
				name = "startTime",
				type = LocalDateTime.class,
				mode = ParameterMode.IN),
			@StoredProcedureParameter(
				name = "endTime",
				type = LocalDateTime.class,
				mode = ParameterMode.IN)
		})
})
public class ChangeFailureRateDetail {

	@Id
	private Long releaseId;

	private String tagName;
	private String releaseName;
	private int commitSize;
	private String repositoryName;
	private int codeChangeSize;
	private LocalDateTime publishedAt;
	private boolean isSuccess;

}