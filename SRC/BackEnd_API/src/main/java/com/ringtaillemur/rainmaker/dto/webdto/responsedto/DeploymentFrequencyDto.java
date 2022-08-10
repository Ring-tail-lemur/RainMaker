package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class DeploymentFrequencyDto {

    private LocalDate start_time;
    private LocalDate end_time;
    private ProductivityLevel level;
    private Map<LocalDate, Integer> deploymentFrequencyMap = new HashMap<>();

    @Builder
    public DeploymentFrequencyDto(LocalDate start_time, LocalDate end_time, ProductivityLevel level,
        Map<LocalDate, Integer> deploymentFrequencyMap) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.level = level;
        this.deploymentFrequencyMap = deploymentFrequencyMap;
    }
}
