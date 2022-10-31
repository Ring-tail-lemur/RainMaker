package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
public class LeadTimeForChangeDetailDto {
	private double codingTime;
	private double pickupTime;
	private double reviewTime;
	private double deployTime;

	public double getTotalValue() {
		return codingTime + pickupTime + reviewTime + deployTime;
	}

	@Builder
	public LeadTimeForChangeDetailDto(double codingTime, double pickupTime, double reviewTime, double deployTime) {
		this.codingTime = codingTime;
		this.pickupTime = pickupTime;
		this.reviewTime = reviewTime;
		this.deployTime = deployTime;
	}

	public LeadTimeForChangeDetailDto(LocalDateTime firstCommitTime, LocalDateTime prOpenTime,
		LocalDateTime firstReviewTime, LocalDateTime prCloseTime, LocalDateTime deploymentTime) {
		this.codingTime = Duration.between(firstCommitTime, prOpenTime).getSeconds() / 60.0;
		this.pickupTime = Duration.between(prOpenTime, firstReviewTime).getSeconds() / 60.0;
		this.reviewTime = Duration.between(firstReviewTime, prCloseTime).getSeconds() / 60.0;
		this.deployTime = Duration.between(prCloseTime, deploymentTime).getSeconds() / 60.0;
	}
}
