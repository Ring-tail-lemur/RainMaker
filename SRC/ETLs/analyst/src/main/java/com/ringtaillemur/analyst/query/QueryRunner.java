package com.ringtaillemur.analyst.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class QueryRunner {
	private static final QueryRunner queryRunner = new QueryRunner();

	private QueryRunner(){}

	public static QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void runQuery(String query) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("azure-mssql-unit").createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager.createNativeQuery(query).executeUpdate();

		transaction.commit();
		entityManager.close();
	}
}
