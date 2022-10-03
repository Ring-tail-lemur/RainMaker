package org.example.functions.loador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.example.functions.dto.LoadingDataDto;

public class DataLoaderImpl implements DataLoader {

	private static final DataLoaderImpl dataLoader = new DataLoaderImpl();

	private DataLoaderImpl() {
	}

	public static DataLoaderImpl getInstance() {
		return dataLoader;
	}

	@Override
	public void load(LoadingDataDto loadingDataDto) {
		runInsertQuery(loadingDataDto.getQuery(), loadingDataDto.getPersistenceUnitName());
	}

	public void runInsertQuery(String query, String getPersistenceUnitName) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnitName);
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
