package com.ringtaillemur.rainmaker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;

public interface OAuthUserRepositoryRepository extends JpaRepository<OAuthUserRepositoryTable, Long> {
	List<OAuthUserRepositoryTable> findByoAuthUser(OAuthUser oAuthUser);

	Optional<OAuthUserRepositoryTable> findByoAuthUserAndRepository(OAuthUser oAuthUser, Repository repository);
}
