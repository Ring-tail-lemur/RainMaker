package com.ringtaillemur.rainmaker.repository;

import java.util.Optional;

import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthUser, Long> {

	Optional<OAuthUser> findById(Long id);

	Optional<OAuthUser> findByUserRemoteId(Long userRemoteId);
	//    @Modifying(clearAutomatically = true)
	//    @Query("UPDATE OAuthUser o SET o.oauth_token = :oauth_token where o.id = :id")
	//    int updateUserToken(@Param(value="oauth_token")String oauth_token, @Param(value="id")Long id);

}
