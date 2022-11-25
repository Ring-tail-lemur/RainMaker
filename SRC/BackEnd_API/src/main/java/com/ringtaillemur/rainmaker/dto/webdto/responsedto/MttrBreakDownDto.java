package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.List;

import lombok.Data;

@Data
public class MttrBreakDownDto {
	private double startAverageTime;
	private double codingAverageTime;
	private double pickupAverageTime;
	private double reviewAverageTime;
	private double deployAverageTime;
	private double convinceAverageTime;

	public MttrBreakDownDto(double startAverageTime, double codingAverageTime, double pickupAverageTime,
		double reviewAverageTime, double deployAverageTime, double convinceAverageTime) {
		this.startAverageTime = startAverageTime;
		this.codingAverageTime = codingAverageTime;
		this.pickupAverageTime = pickupAverageTime;
		this.reviewAverageTime = reviewAverageTime;
		this.deployAverageTime = deployAverageTime;
		this.convinceAverageTime = convinceAverageTime;
	}

	public MttrBreakDownDto(List<MttrDetailDto> timeToRestoreServiceDetail) {
		this.startAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getStartTime)
			.average()
			.orElse(0);

		this.codingAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getCodingTime)
			.average()
			.orElse(0);

		this.pickupAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getPickupTime)
			.average()
			.orElse(0);
		this.reviewAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getReviewTime)
			.average()
			.orElse(0);
		this.deployAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getDeployTime)
			.average()
			.orElse(0);
		this.convinceAverageTime = timeToRestoreServiceDetail.stream()
			.mapToLong(MttrDetailDto::getConvinceTime)
			.average()
			.orElse(0);
	}
}
