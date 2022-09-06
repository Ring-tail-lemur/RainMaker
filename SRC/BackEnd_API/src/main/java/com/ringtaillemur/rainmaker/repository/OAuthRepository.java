package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthUser, Long> {

    Optional<OAuthUser> findById(Long id);

    Optional<OAuthUser> findByUserRemoteId(Long user_remote_id);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE OAuthUser o SET o.oauth_token = :oauth_token where o.id = :id")
//    int updateUserToken(@Param(value="oauth_token")String oauth_token, @Param(value="id")Long id);

}
