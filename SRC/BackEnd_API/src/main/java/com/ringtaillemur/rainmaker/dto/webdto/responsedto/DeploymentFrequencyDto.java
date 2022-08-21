package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDate;
import java.util.Map;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Data;

@Data
public class DeploymentFrequencyDto {

	private LocalDate startTime;
	private LocalDate endTime;
	private ProductivityLevel level;
	private Map<LocalDate, Integer> deploymentFrequencyMap;

	public DeploymentFrequencyDto(LocalDate startTime, LocalDate endTime,
		Map<LocalDate, Integer> deploymentFrequencyMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.deploymentFrequencyMap = deploymentFrequencyMap;
		this.level = getDeploymentFrequencyProductivityLevel();
	}

	private ProductivityLevel getDeploymentFrequencyProductivityLevel() {
		double deploymentFrequency = getDeploymentFrequency();
		if (deploymentFrequency < 1440)
			return ProductivityLevel.FRUIT;
		if (deploymentFrequency < 10080)
			return ProductivityLevel.FLOWER;
		if (deploymentFrequency < 43200)
			return ProductivityLevel.FLOWER;
		return ProductivityLevel.SEED;
	}

	private double getDeploymentFrequency() {
		double averageDeploymentNumber = deploymentFrequencyMap.values().stream()
			.mapToDouble(deploymentFrequency -> deploymentFrequency)
			.average()
			.orElse(0);
		return 1 / averageDeploymentNumber * 1440;
	}
}
