package com.ringtaillemur.analyst.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RepositoryRepositoryTest {

	private final RepositoryRepository repositoryRepository = new RepositoryRepository();

	@Test
	void getOneTokenByRepositoryIdTest() {
		String token = repositoryRepository.getOneTokenByRepositoryId("517528822");
		System.out.println("token = " + token);
	}

}
