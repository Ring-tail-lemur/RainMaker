package com.ringtaillemur.analyst.analysislogic.dorametric;

import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;

public class TimeToRestoreService {
	private static final TimeToRestoreService timeToRestoreService = new TimeToRestoreService();
	private final QueryRunner queryRunner = QueryRunner.getQueryRunner();

	private TimeToRestoreService() {
	}

	public static TimeToRestoreService getTimeToRestoreService() {
		return timeToRestoreService;
	}

	public void calculateTimeToRestoreService() {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_TIME_TO_RESTORE_SERVICE);
	}
}
