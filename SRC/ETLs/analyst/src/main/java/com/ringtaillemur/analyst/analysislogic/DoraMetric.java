package com.ringtaillemur.analyst.analysislogic;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;

import com.ringtaillemur.analyst.domain.LeadTimeForChange;
import com.ringtaillemur.analyst.query.OlapQuery;

public class DoraMetric {

	private static final DoraMetric doraMetric = new DoraMetric();

	private DoraMetric() {
	}

	public static DoraMetric getDoraMetric() {
		return doraMetric;
	}

	private EntityManager getEntityManager() {
		return Persistence.createEntityManagerFactory("azure-mssql-unit").createEntityManager();
	}

	private Session getSession() {
		return getEntityManager().unwrap(Session.class);
	}

	public void calculateLeadTimeForChange() {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.createNativeQuery(OlapQuery.MAKE_LEAD_TIME_FOR_CHANGE, LeadTimeForChange.class).executeUpdate();

		transaction.commit();

		entityManager.close();
	}
}
