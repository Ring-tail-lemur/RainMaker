package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.service.UtilService;

// @SpringBootTest
class ChangeFailureRateServiceTest {
	//
	// @Test
	//
	// void makeReleaseCountMapFunctionTest() {
	// 	ChangeFailureRateDto changeFailureRate = changeFailureRateService.getChangeFailureRate(1L,
	// 		LocalDate.of(2022, 8, 1), LocalDate.of(2022, 9, 1));
	// 	System.out.println(changeFailureRate);
	// }
	//
	// @Test
	// void makeEmptyChangeFailureListMapTest() {
	// 	List<ReleaseSuccess> failedChangeList = new ArrayList<>();
	//
	// 	ReleaseSuccess failedChange1 = new ReleaseSuccess(LocalDateTime.now().minusDays(4));
	// 	failedChangeList.add(failedChange1);
	// 	ReleaseSuccess failedChange2 = new ReleaseSuccess(LocalDateTime.now().minusDays(3));
	// 	failedChangeList.add(failedChange2);
	// 	ReleaseSuccess failedChange3 = new ReleaseSuccess(LocalDateTime.now().minusDays(2));
	// 	failedChangeList.add(failedChange3);
	// 	ReleaseSuccess failedChange4 = new ReleaseSuccess(LocalDateTime.now().minusDays(1));
	// 	failedChangeList.add(failedChange4);
	// 	ReleaseSuccess failedChange5 = new ReleaseSuccess(LocalDateTime.now().minusDays(1));
	// 	failedChangeList.add(failedChange5);
	//
	// 	Map<LocalDate, Integer> localDateListMap = changeFailureRateService.getChangeFailureCountMap(
	// 		failedChangeList);
	// 	System.out.println(localDateListMap);
	// }

//	@Test
//	void 뭔가이상하다() {
//		List<LeadTimeForChange> leadTimeForChangeList = new ArrayList<>();
//
//		leadTimeForChangeList.add(
//			new LeadTimeForChange(
//				LocalDateTime.of(2022, 6, 11, 5, 30),
//				LocalDateTime.of(2022, 6, 12, 5, 30),
//				LocalDateTime.of(2022, 6, 13, 5, 30),
//				LocalDateTime.of(2022, 6, 14, 5, 30)
//			)
//		);
//		leadTimeForChangeList.add(
//			new LeadTimeForChange(
//				LocalDateTime.of(2022, 6, 12, 5, 30),
//				LocalDateTime.of(2022, 6, 14, 5, 30),
//				LocalDateTime.of(2022, 6, 16, 5, 30),
//				LocalDateTime.of(2022, 6, 18, 5, 30)
//			)
//		);
//		leadTimeForChangeList.add(
//			new LeadTimeForChange(
//				LocalDateTime.of(2022, 6, 13, 5, 30),
//				LocalDateTime.of(2022, 6, 15, 5, 30),
//				LocalDateTime.of(2022, 6, 17, 5, 30),
//				LocalDateTime.of(2022, 6, 19, 5, 30)
//			)
//		);
//		leadTimeForChangeList.add(
//			new LeadTimeForChange(
//				LocalDateTime.of(2022, 6, 14, 5, 30),
//				LocalDateTime.of(2022, 6, 16, 5, 30),
//				LocalDateTime.of(2022, 6, 18, 5, 30),
//				LocalDateTime.of(2022, 6, 20, 5, 30)
//			)
//		);
//		leadTimeForChangeList.add(
//			new LeadTimeForChange(
//				LocalDateTime.of(2022, 6, 5, 5, 30),
//				LocalDateTime.of(2022, 6, 7, 5, 30),
//				LocalDateTime.of(2022, 6, 19, 5, 30),
//				LocalDateTime.of(2022, 6, 21, 5, 30)
//			)
//		);
//
//		UtilService utilService = new UtilService();
//		Map<LocalDate, Double> localDateDoubleMap = utilService.makeDailyAverageMap(leadTimeForChangeList,
//			x -> x.getDeploymentTime().toLocalDate(),
//			LeadTimeForChange::getLeadTimeForChange);
//		System.out.println("localDateDoubleMap = " + localDateDoubleMap);
//	}
}
