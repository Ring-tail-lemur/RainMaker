package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CycleTimeDetailDto {

	private double codingTime;
	private ProductivityLevel codingTimeLevel;

	private double pickupTime;
	private ProductivityLevel pickupTimeLevel;

	private double reviewTime;
	private ProductivityLevel reviewTimeLevel;

	private double deployTime;
	private ProductivityLevel deployTimeLevel;
}
