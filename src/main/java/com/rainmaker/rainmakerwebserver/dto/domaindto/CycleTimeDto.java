package com.rainmaker.rainmakerwebserver.dto.domaindto;

import org.threeten.bp.LocalDateTime;

import com.google.cloud.bigquery.FieldValueList;

import lombok.Builder;
import lombok.Data;

@Data
public class CycleTimeDto {
	private int cycleTimeId;
	private int gitRepositoryId;
	private int organizationId;
	private LocalDateTime cycleTimeStart;
	private LocalDateTime cycleTimeEnd;
	private int codingTime;
	private int pickupTime;
	private int reviewTime;
	private int deploymentTime;
	private int commitId;

	public CycleTimeDto(FieldValueList fieldValues) {
		cycleTimeId = Integer.parseInt((String)fieldValues.get(0).getValue());
		gitRepositoryId = Integer.parseInt((String)fieldValues.get(1).getValue());
		organizationId = Integer.parseInt((String)fieldValues.get(2).getValue());
		cycleTimeStart = LocalDateTime.parse((CharSequence)fieldValues.get(3).getValue());
		cycleTimeEnd = LocalDateTime.parse((CharSequence)fieldValues.get(4).getValue());
		codingTime = Integer.parseInt((String)fieldValues.get(5).getValue());
		pickupTime = Integer.parseInt((String)fieldValues.get(6).getValue());
		reviewTime = Integer.parseInt((String)fieldValues.get(7).getValue());
		deploymentTime = Integer.parseInt((String)fieldValues.get(8).getValue());
		commitId = Integer.parseInt((String)fieldValues.get(9).getValue());
	}

	@Builder
	public CycleTimeDto(int cycleTimeId, int gitRepositoryId, int organizationId, LocalDateTime cycleTimeStart,
		LocalDateTime cycleTimeEnd, int codingTime, int pickupTime, int reviewTime, int deploymentTime,
		int commitId) {
		this.cycleTimeId = cycleTimeId;
		this.gitRepositoryId = gitRepositoryId;
		this.organizationId = organizationId;
		this.cycleTimeStart = cycleTimeStart;
		this.cycleTimeEnd = cycleTimeEnd;
		this.codingTime = codingTime;
		this.pickupTime = pickupTime;
		this.reviewTime = reviewTime;
		this.deploymentTime = deploymentTime;
		this.commitId = commitId;
	}
}
