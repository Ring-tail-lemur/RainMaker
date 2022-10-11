package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.io.IOException;

import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;

public class ChangeFailureRate {

	private static final ChangeFailureRate changeFailureRate = new ChangeFailureRate();
	private final QueryRunner queryRunner = QueryRunner.getQueryRunner();

	private ChangeFailureRate() {
	}

	public static ChangeFailureRate getChangeFailureRate() {
		return changeFailureRate;
	}

	public void calculateChangeFailureRate() throws IOException {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_CHANGE_FAILURE_RATE);
	}
}
