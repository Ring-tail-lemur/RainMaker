package com.ringtaillemur.analyst.query;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.json.simple.parser.ParseException;

import com.ringtaillemur.analyst.dto.ReleaseDto;
import com.ringtaillemur.analyst.restapi.LogModule;

public class QueryRunner {
	private static final QueryRunner queryRunner = new QueryRunner();

	private QueryRunner() {
	}

	public static QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void runUpdateInsertQuery(String query) throws IOException, ParseException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.createNativeQuery(query).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			LogModule logModule = new LogModule();
			logModule.sendLog(e, "QueryRunner.java // runUpdateInsertQuery");
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

	public ReleaseDto runSelectCalculatedReleaseTop1Query(String query) throws IOException, ParseException {
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

	public void runMakePullRequestDirection(String query) throws IOException, ParseException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			List list = entityManager.createNativeQuery(query).getResultList();
		} catch (Exception e) {
			LogModule logModule = new LogModule();
			logModule.sendLog(e, "QueryRunner // runMakePullRequestDirection");
		}

	}
}
