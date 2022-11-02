package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.CycleTimeDetailsBySourcePrDto;
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
	
	@Test
	void getLeadTimeForChangeDetails()
	{
		//given
		LocalDate start = LocalDate.of(2022, 9, 23);
		LocalDate end = LocalDate.now();
		List<Long> repositoryIds = new ArrayList<>();
		repositoryIds.add(510731046L);


		//when
		List<CycleTimeDetailsBySourcePrDto> cycleTimeDetailsBySourcePrDto = leadTimeForChangeService.getCycleTimeDetailsBySourcePrDto(
			repositoryIds, start, end);
		System.out.println("cycleTimeDetailsBySourcePrDto = " + cycleTimeDetailsBySourcePrDto);



		//then
	}

}
