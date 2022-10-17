package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LeadTimeForChangeServiceTest {


	@Autowired
	LeadTimeForChangeService leadTimeForChangeService;


	@Test
	void getLeadTimeForChangeByTimeTest() {
		ArrayList<Long> repositoryIds = new ArrayList<>() {{
			add(510731046L);
		}};
		LeadTimeForChangeByTimeDto leadTimeForChangeByTime = leadTimeForChangeService.getLeadTimeForChangeByTime(
			repositoryIds, LocalDate.of(2022, 9, 14), LocalDate.now());
		System.out.println(leadTimeForChangeByTime);
	}

}
