package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthUser, Long> {

    Optional<OAuthUser> findById(Long id);

}
