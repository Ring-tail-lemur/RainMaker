package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CycleTimeDetailDto {

	private double codingAverageTime;
	private ProductivityLevel codingTimeLevel;
	private double pickupAverageTime;
	private ProductivityLevel pickupTimeLevel;
	private double reviewAverageTime;
	private ProductivityLevel reviewTimeLevel;
	private double deployAverageTime;
	private ProductivityLevel deployTimeLevel;


	@Builder
	public CycleTimeDetailDto(List<LeadTimeForChangeDetailDto> leadTimeForChangeDetailDtos) {

		this.codingAverageTime = leadTimeForChangeDetailDtos.stream()
			.mapToDouble(LeadTimeForChangeDetailDto::getCodingTime)
			.average()
			.orElse(0);

		this.pickupAverageTime = leadTimeForChangeDetailDtos.stream()
			.mapToDouble(LeadTimeForChangeDetailDto::getPickupTime)
			.average()
			.orElse(0);

		this.reviewAverageTime = leadTimeForChangeDetailDtos.stream()
			.mapToDouble(LeadTimeForChangeDetailDto::getReviewTime)
			.average()
			.orElse(0);

		this.deployAverageTime = leadTimeForChangeDetailDtos.stream()
			.mapToDouble(LeadTimeForChangeDetailDto::getDeployTime)
			.average()
			.orElse(0);

		this.codingTimeLevel = getCodingTimeProductivityLevel();
		this.pickupTimeLevel = getPickUpTimeProductivityLevel();
		this.reviewTimeLevel = getReviewTimeProductivityLevel();
		this.deployTimeLevel = getDeployTimeProductivityLevel();
	}

	private ProductivityLevel getCodingTimeProductivityLevel() {
		if(codingAverageTime >= 39 * 60)
			return ProductivityLevel.SEED;
		if(codingAverageTime >= 24 * 60)
			return ProductivityLevel.SPROUT;
		if(codingAverageTime >= 12 * 60)
			return ProductivityLevel.FLOWER;
		return ProductivityLevel.FRUIT;
	}

	private ProductivityLevel getPickUpTimeProductivityLevel() {
		if(pickupAverageTime >= 19 * 60)
			return ProductivityLevel.SEED;
		if(pickupAverageTime >= 12 * 60)
			return ProductivityLevel.SPROUT;
		if(pickupAverageTime >= 7 * 60)
			return ProductivityLevel.FLOWER;
		return ProductivityLevel.FRUIT;
	}

	private ProductivityLevel getReviewTimeProductivityLevel() {
		if(reviewAverageTime >= 29 * 60)
			return ProductivityLevel.SEED;
		if(reviewAverageTime >= 13 * 60)
			return ProductivityLevel.SPROUT;
		if(reviewAverageTime >= 6 * 60)
			return ProductivityLevel.FLOWER;
		return ProductivityLevel.FRUIT;
	}

	private ProductivityLevel getDeployTimeProductivityLevel() {
		if(deployAverageTime >= 8 * 24 * 60)
			return ProductivityLevel.SEED;
		if(deployAverageTime >= 2 * 24 * 60)
			return ProductivityLevel.SPROUT;
		if(deployAverageTime >= 4 * 60)
			return ProductivityLevel.FLOWER;
		return ProductivityLevel.FRUIT;
	}
}
