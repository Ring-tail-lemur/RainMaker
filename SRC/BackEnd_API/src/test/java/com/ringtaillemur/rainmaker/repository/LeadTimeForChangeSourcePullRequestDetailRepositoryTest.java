package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetail;

@SpringBootTest
class LeadTimeForChangeSourcePullRequestDetailRepositoryTest {
	@Autowired
	LeadTimeForChangeSourcePullRequestDetailRepository leadTimeForChangeSourcePullRequestDetailRepository;

	@Test
	void test() {
		List<LeadTimeForChangeSourcePullRequestDetail> all = leadTimeForChangeSourcePullRequestDetailRepository.findAll();
	}

	@Test
	void queryTest() {
		List<LeadTimeForChangeSourcePullRequestDetail> byDataLeadTimeForChangeIdIn =
			leadTimeForChangeSourcePullRequestDetailRepository.findByDataLeadTimeForChangeIdIn(List.of(2310L, 2311L, 2312L, 2313L));
		System.out.println("byDataLeadTimeForChangeIdIn = " + byDataLeadTimeForChangeIdIn);
	}
}
