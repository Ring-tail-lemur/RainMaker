package com.ringtaillemur.rainmaker.repository;

import static org.junit.jupiter.api.Assertions.*;

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

}
