package org.example.functions.db_service;

import org.example.functions.dao.QueryRunner;
import org.junit.jupiter.api.Test;

class QueryRunnerTest {

	private QueryRunner queryRunner = QueryRunner.getQueryRunner();

	@Test
	void runInsertQueryTest() {
		queryRunner.runInsertQuery("INSERT INTO release_event (release_event_id, release_event_type, release_id)\n"
			+ "VALUES ('DB11BA20-275C-11ED-854A-1165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-2165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-3165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-4165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-5165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-6165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-7165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-9165CEA94DEB', 'test', 30),\n"
			+ "       ('DB11BA20-275C-11ED-854A-0165CEA94DEB', 'test', 30)");
	}

}
