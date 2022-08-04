package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.Map;

import com.ringtaillemur.rainmaker.util.contant.productiveity_boundery.CycleTimeProductivityBounder;
import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

		private ProductivityLevel calculateCycleTimeProductivityLevel(Long totalCycleTime) {
			if (totalCycleTime < CycleTimeProductivityBounder.SEED_AND_SPROUT_BOUNDER) {
				return ProductivityLevel.FRUIT;
			}
			if (totalCycleTime < CycleTimeProductivityBounder.SPROUT_AND_FLOWER_BOUNDER) {
				return ProductivityLevel.FLOWER;
			}
			if (totalCycleTime < CycleTimeProductivityBounder.FLOWER_AND_FRUIT_BOUNDER) {
				return ProductivityLevel.SPROUT;
			}
			return ProductivityLevel.SEED;
		}
	}
}
