package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;

public interface OAuthUserRepositoryRepository extends JpaRepository<OAuthUserRepositoryTable, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM OAuthUserRepositoryTable o WHERE o.oAuthUser.userRemoteId = :userRemoteId")
	void deleteByOAuthUserIdQuery(@Param("userRemoteId") Long userRemoteId);

	@Query(value = "SELECT o FROM OAuthUserRepositoryTable o WHERE o.oAuthUser.userRemoteId = :userRemoteId")
	List<OAuthUserRepositoryTable> findByOAuthUserIdQuery(@Param("userRemoteId") Long userRemoteId);

	List<OAuthUserRepositoryTable> findByoAuthUser(OAuthUser oAuthUser);

	List<OAuthUserRepositoryTable> findByRepository(Repository repository);

	//    @Query(nativeQuery = true, value = "")

}
