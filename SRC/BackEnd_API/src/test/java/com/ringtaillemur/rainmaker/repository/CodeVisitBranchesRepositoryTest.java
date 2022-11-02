package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ringtaillemur.rainmaker.domain.view.CodeVisitBranches;

@SpringBootTest
class CodeVisitBranchesRepositoryTest {
	@Autowired
	CodeVisitBranchesRepository codeVisitBranchesRepository;

	@Test
	void test() {
		List<CodeVisitBranches> all = codeVisitBranchesRepository.findAll();
	}

}
