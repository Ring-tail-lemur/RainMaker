package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuthUser, Long> {
    Optional<OAuthUser> findById(Long id);
}
