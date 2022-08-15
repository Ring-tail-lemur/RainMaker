package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

@Data
public class DeploymentFrequencyDto {

    private LocalDate startTime;
    private LocalDate endTime;
    private ProductivityLevel level;
    private Map<LocalDate, Integer> deploymentFrequencyMap = new HashMap<>();

    @Builder
    public DeploymentFrequencyDto(LocalDate startTime, LocalDate endTime,
        Map<LocalDate, Integer> deploymentFrequencyMap) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.deploymentFrequencyMap = deploymentFrequencyMap;
        Integer averageDeploymentFrequency = getAverageDeploymentFrequency(deploymentFrequencyMap);
        this.level = getDeploymentFrequencyProductivityLevel(averageDeploymentFrequency);
    }

    private ProductivityLevel getDeploymentFrequencyProductivityLevel(Integer deploymentFrequency) {
        if(deploymentFrequency < 1440) return ProductivityLevel.FRUIT;
        if(deploymentFrequency < 10080) return ProductivityLevel.FLOWER;
        if(deploymentFrequency < 43200) return ProductivityLevel.FLOWER;
        return ProductivityLevel.SEED;
    }

    private Integer getAverageDeploymentFrequency(Map<LocalDate, Integer> deploymentFrequencyMap){
        return (int) deploymentFrequencyMap.values().stream()
            .mapToInt(leadTimeForChange -> leadTimeForChange)
            .average()
            .orElseThrow(() -> new NullArgumentException("nothing to average operation values"));
    }
}
