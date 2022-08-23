package com.ringtaillemur.analyst.analysislogic.dorametric;

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

	public void calculateLeadTimeForChange() {
		calculateLeadTimeForChangeExceptDeploymentTime();
		calculateDeploymentTime();
	}

	private void calculateLeadTimeForChangeExceptDeploymentTime() {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_LEAD_TIME_FOR_CHANGE);
	}

	private void calculateDeploymentTime() {
		queryRunner.runUpdateInsertQuery(OlapQuery.MAKE_DEPLOY_TIME);
	}
}
