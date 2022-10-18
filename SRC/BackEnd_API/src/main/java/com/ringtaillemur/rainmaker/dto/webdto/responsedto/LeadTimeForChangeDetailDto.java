package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeadTimeForChangeDetailDto {
	private double codingTime;
	private double pickupTime;
	private double reviewTime;
	private double deployTime;

	public double getTotalValue() {
		return codingTime + pickupTime + reviewTime + deployTime;
	}
}
