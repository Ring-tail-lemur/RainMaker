package com.ringtaillemur.analyst.analysislogic.dorametric;

import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;

public class ChangeFailureRate {

	private static final ChangeFailureRate changeFailureRate = new ChangeFailureRate();
	private QueryRunner queryRunner = QueryRunner.getQueryRunner();

	private ChangeFailureRate() {
	}

	public static ChangeFailureRate getChangeFailureRate() {
		return changeFailureRate;
	}

	public void calculateChangeFailureRate() {
		queryRunner.runQuery(OlapQuery.MAKE_CHANGE_FAILURE_RATE);
	}
}
