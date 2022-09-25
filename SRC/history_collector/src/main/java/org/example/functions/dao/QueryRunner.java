package org.example.functions.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class QueryRunner {
	private static final QueryRunner queryRunner = new QueryRunner();

	private QueryRunner() {
	}

	public static QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void runInsertQueries(List<String> queries) {
		queries.forEach(this::runInsertQuery);
	}

	public void runInsertQuery(String query) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.createNativeQuery(query).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
}
