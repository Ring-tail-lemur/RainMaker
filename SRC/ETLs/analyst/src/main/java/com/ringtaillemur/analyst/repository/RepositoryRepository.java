package com.ringtaillemur.analyst.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RepositoryRepository {

	public String getOneTokenByRepositoryId(String repositoryId) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String token = null;
		try {
			token = entityManager.createNativeQuery(""
					+ "select top 1 ou.oauth_token "
					+ "from repository r "
					+ "         join oauth_user_repository_table ourt on r.repository_id = ourt.repository_id "
					+ "         join oauth_user ou on ourt.oauth_user_id = ou.user_remote_id "
					+ "where r.repository_id = :repositoryId")
				.setParameter("repositoryId", repositoryId)
				.getResultStream()
				.findFirst()
				.orElseThrow(() -> new Exception("해당 Repository에 속해있는 사람의 Token을 조회할 수 없습니다."))
				.toString();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		entityManager.close();
		entityManagerFactory.close();
		return token;
	}
}
