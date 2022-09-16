package com.ringtaillemur.analyst.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.qlrm.mapper.JpaResultMapper;

import com.ringtaillemur.analyst.dto.ReleaseDto;

public class QueryRunner {
	private static final QueryRunner queryRunner = new QueryRunner();
	private final JpaResultMapper jpaResultMapper = new JpaResultMapper();

	private QueryRunner() {
	}

	public static QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void runUpdateInsertQuery(String query) {
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

	public List<ReleaseDto> runSelectReleaseQuery(String query) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Object[]> resultList = entityManager.createNativeQuery(query).getResultList();
		List<ReleaseDto> releaseDtoList = resultList.stream()
			.map(result -> new ReleaseDto(result[0].toString(), (String)result[1], (String)result[2],
				(String)result[3], result[4].toString())).collect(Collectors.toList());
		entityManager.close();
		entityManagerFactory.close();
		return releaseDtoList;
	}

	public ReleaseDto runSelectCalculatedReleaseTop1Query(String query) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Object[] result;
		ReleaseDto releaseDto;
		try {
			result = ((Object[])entityManager.createNativeQuery(query).getSingleResult());
			releaseDto = new ReleaseDto(result[0].toString(), (String)result[1], (String)result[2],
				(String)result[3], result[4].toString());
		} catch (Exception e) {
			releaseDto = new ReleaseDto();
		}
		entityManager.close();
		entityManagerFactory.close();
		return releaseDto;
	}
}
