package com.ringtaillemur.analyst.analysislogic.dorametric;

import java.io.IOException;

import com.ringtaillemur.analyst.query.OlapQuery;
import com.ringtaillemur.analyst.query.QueryRunner;

public class LeadTimeForChange {

	private static final LeadTimeForChange leadTimeForChange = new LeadTimeForChange();
	private final QueryRunner queryRunner = QueryRunner.getQueryRunner();

	private LeadTimeForChange() {
	}

	public static LeadTimeForChange getLeadTimeForChange() {
		return leadTimeForChange;
	}

	public void calculateLeadTimeForChange() throws IOException {
		calculateLeadTimeForChangeExceptDeploymentTime();
		calculateDeploymentTime();
	}

	private void calculateLeadTimeForChangeExceptDeploymentTime() throws IOException {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_LEAD_TIME_FOR_CHANGE);
	}

	private void calculateDeploymentTime() throws IOException {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_DEPLOY_TIME);
	}
}
