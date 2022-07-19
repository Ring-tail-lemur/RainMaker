package com.rainmaker.rainmakerwebserver.domain.bigquery.analysis.delivery;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rainmaker.rainmakerwebserver.repository.BigqueryCycleTimeRepository;

@SpringBootTest
class BigqueryCycleTimeRepositoryTest {

	@Autowired
	private BigqueryCycleTimeRepository bigqueryCycleTimeRepository;

	@Test
	public void test1() throws InterruptedException {
		bigqueryCycleTimeRepository.findCycleTimesByCycleTimeEndBetween(LocalDateTime.now().minusDays(7),
			LocalDateTime.now());
	}

}
