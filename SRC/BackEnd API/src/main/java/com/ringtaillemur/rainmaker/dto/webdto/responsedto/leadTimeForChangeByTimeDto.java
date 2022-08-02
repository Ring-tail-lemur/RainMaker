package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class leadTimeForChangeByTimeDto {

    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private ProductivityLevel level;
    private List<Integer> leadTimeForChangeAverage = new ArrayList<>();
    
}
