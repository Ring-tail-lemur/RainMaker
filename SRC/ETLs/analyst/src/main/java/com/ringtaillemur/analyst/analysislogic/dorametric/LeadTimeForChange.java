package com.ringtaillemur.analyst.analysislogic.dorametric;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ringtaillemur.analyst.query.OlapQuery;

public class LeadTimeForChange {

	private static final LeadTimeForChange LEAD_TIME_FOR_CHANGE = new LeadTimeForChange();

	private LeadTimeForChange() {
	}

	public static LeadTimeForChange getLeadTimeForChange() {
		return LEAD_TIME_FOR_CHANGE;
	}

	public void calculateLeadTimeForChange() {
		calculateLeadTimeForChangeExceptDeploymentTime();
		calculateDeploymentTime();
	}

	private void calculateLeadTimeForChangeExceptDeploymentTime() {
		EntityManager entityManager = Persistence.createEntityManagerFactory("azure-mssql-unit").createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.createNativeQuery(OlapQuery.MAKE_LEAD_TIME_FOR_CHANGE,
			com.ringtaillemur.analyst.domain.LeadTimeForChange.class).executeUpdate();

		transaction.commit();

		entityManager.close();
	}

	private void calculateDeploymentTime() {

		EntityManager entityManager = Persistence.createEntityManagerFactory("azure-mssql-unit").createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.createNativeQuery(OlapQuery.MAKE_DEPLOY_TIME,
			com.ringtaillemur.analyst.domain.LeadTimeForChange.class).executeUpdate();

		transaction.commit();

		entityManager.close();
	}
}
