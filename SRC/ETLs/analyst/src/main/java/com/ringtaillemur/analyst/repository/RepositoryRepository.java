package com.ringtaillemur.analyst.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ringtaillemur.analyst.dto.ReleaseDto;

public class RepositoryRepository {

	public String getOneTokenByRepositoryId(String repositoryId) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("azure-mssql-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String token = entityManager.createNativeQuery(""
				+ "select top 1 ou.oauth_token "
				+ "from repository r "
				+ "         join oauth_user_repository_table ourt on r.repository_id = ourt.repository_id "
				+ "         join oauth_user ou on ourt.oauth_user_id = ou.user_remote_id "
				+ "where r.repository_id = :repositoryId")
			.setParameter("repositoryId", repositoryId)
			.getSingleResult().toString();
		entityManager.close();
		entityManagerFactory.close();
		return token;
	}
}
