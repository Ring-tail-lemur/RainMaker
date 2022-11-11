package com.ringtaillemur.rainmaker.domain.procedure;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "ReleaseDetail")
@Getter
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		name = "getReleaseDetailList",
		procedureName = "releaseDetail",
		resultClasses = {ReleaseDetail.class},
		parameters = {
			@StoredProcedureParameter(
				name = "repoString",
				type = String.class,
				mode = ParameterMode.IN)
		})
})
public class ReleaseDetail {

	@Id
	private Long releaseId;

	private String tagName;
	private String releaseName;
	private int commitSize;
	private String repositoryName;
	private int codeChangeSize;

}