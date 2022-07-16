package com.rainmaker.rainmakerwebserver.dto.controllerdto.responsedto;

import java.util.Map;

import com.rainmaker.rainmakerwebserver.util.contant.productiveity_boundery.CycleTimeProductivityBounder;
import com.rainmaker.rainmakerwebserver.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

@Data
public class MainCycleTimeResponseDto {

	private Long totalCycleTime;
	private CycleTimeDto cycleTimeDto = new CycleTimeDto();
	private ProductivityLevel productivityLevel;

	@Builder
	MainCycleTimeResponseDto(Long codingTime, Long pickupTime, Long reviewTime, Long deploymentTime) {
		cycleTimeDto.codingTime = codingTime;
		cycleTimeDto.pickupTime = pickupTime;
		cycleTimeDto.reviewTime = reviewTime;
		cycleTimeDto.deploymentTime = deploymentTime;
		totalCycleTime = cycleTimeDto.getTotalCycleTime();
		productivityLevel = cycleTimeDto.calculateCycleTimeProductivityLevel(totalCycleTime);
	}

	public MainCycleTimeResponseDto(Map<String, Long> cycleTime) {
		cycleTimeDto.codingTime = cycleTime.get("codingTime");
		cycleTimeDto.pickupTime = cycleTime.get("pickupTime");
		cycleTimeDto.reviewTime = cycleTime.get("reviewTime");
		cycleTimeDto.deploymentTime = cycleTime.get("deploymentTime");
		totalCycleTime = cycleTimeDto.getTotalCycleTime();
		productivityLevel = cycleTimeDto.calculateCycleTimeProductivityLevel(totalCycleTime);
	}

	@Data
	private class CycleTimeDto {
		//모든 시간은 minute 이다.
		Long codingTime;
		Long pickupTime;
		Long reviewTime;
		Long deploymentTime;

		private Long getTotalCycleTime() {
			return codingTime + pickupTime + reviewTime + deploymentTime;
		}

		;

		public ProductivityLevel calculateCycleTimeProductivityLevel(Long totalCycleTime) {
			if (totalCycleTime < CycleTimeProductivityBounder.SEED_AND_SPROUT_BOUNDER) {
				return ProductivityLevel.Fruit;
			}
			if (totalCycleTime < CycleTimeProductivityBounder.SPROUT_AND_FLOWER_BOUNDER) {
				return ProductivityLevel.Flower;
			}
			if (totalCycleTime < CycleTimeProductivityBounder.FLOWER_AND_FRUIT_BOUNDER) {
				return ProductivityLevel.Sprout;
			}
			return ProductivityLevel.SEED;
		}
	}
}
