package com.rainmaker.rainmakerwebserver.dto.controllerdto.responsedto;

import java.util.Map;

import com.rainmaker.rainmakerwebserver.util.contant.productiveity_boundery.CycleTimeProductivityBounder;
import com.rainmaker.rainmakerwebserver.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

@Data
public class MainCycleTimeResponseDto {

	private Long totalCycleTime;
	private AverageCycleTime averageCycleTime = new AverageCycleTime();
	private ProductivityLevel productivityLevel;

	@Builder
	MainCycleTimeResponseDto(Long codingTime, Long pickupTime, Long reviewTime, Long deploymentTime) {
		averageCycleTime.codingTime = codingTime;
		averageCycleTime.pickupTime = pickupTime;
		averageCycleTime.reviewTime = reviewTime;
		averageCycleTime.deploymentTime = deploymentTime;
		totalCycleTime = averageCycleTime.getTotalCycleTime();
		productivityLevel = averageCycleTime.calculateCycleTimeProductivityLevel(totalCycleTime);
	}

	public MainCycleTimeResponseDto(Map<String, Long> cycleTime) {
		this.averageCycleTime.codingTime = cycleTime.get("codingTime");
		this.averageCycleTime.pickupTime = cycleTime.get("pickupTime");
		this.averageCycleTime.reviewTime = cycleTime.get("reviewTime");
		this.averageCycleTime.deploymentTime = cycleTime.get("deploymentTime");
		totalCycleTime = this.averageCycleTime.getTotalCycleTime();
		productivityLevel = this.averageCycleTime.calculateCycleTimeProductivityLevel(totalCycleTime);
	}

	@Data
	private class AverageCycleTime {
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
