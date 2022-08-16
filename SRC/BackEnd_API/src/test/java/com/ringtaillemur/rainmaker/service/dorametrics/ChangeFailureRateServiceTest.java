package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;

@SpringBootTest
class ChangeFailureRateServiceTest {

	@Autowired
	private ChangeFailureRateService changeFailureRateService;

	@Test

	void makeReleaseCountMapFunctionTest() {
		ChangeFailureRateDto changeFailureRate = changeFailureRateService.getChangeFailureRate(1L,
			LocalDate.of(2022, 8, 1), LocalDate.of(2022, 9, 1));
		System.out.println(changeFailureRate);
	}
}
