package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class leadTimeForChangeByTimeDto {

    private LocalDate start_time;
    private LocalDate end_time;
    private ProductivityLevel level;
    private Map<LocalDate, Integer> leadTimeForChangeAverageMap = new HashMap<>();
    
}
