package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OAuthUserRepositoryRepository extends JpaRepository<OAuthUserRepositoryTable, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM OAuthUserRepositoryTable o WHERE o.oAuthUser.userRemoteId = :userRemoteId")
    void deleteByOAuthUserIdQuery(@Param("userRemoteId") Long userRemoteId);

}
