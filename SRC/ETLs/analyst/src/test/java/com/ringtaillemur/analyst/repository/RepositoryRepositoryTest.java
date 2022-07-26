package com.ringtaillemur.analyst.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class RepositoryRepositoryTest {

	private final RepositoryRepository repositoryRepository = new RepositoryRepository();

	@Test
	void getOneTokenByRepositoryIdTest() throws IOException, ParseException {
		String token = repositoryRepository.getOneTokenByRepositoryId("517528822");
		System.out.println("token = " + token);
	}

}
