package com.ringtaillemur.rainmaker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.OAuthUserRepositoryTable;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.repository.OAuthUserRepositoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthUserRepositoryService {
	private final OAuthUserRepositoryRepository oAuthUserRepositoryRepository;

	public List<OAuthUserRepositoryTable> getRepositoryByOAuthUser(OAuthUser oAuthUser) {
		return oAuthUserRepositoryRepository.findByoAuthUser(oAuthUser);
	}

	public List<OAuthUserRepositoryTable> getOAuthUserRepositoryTableByOAuthUser(OAuthUser oAuthUser) {
		return oAuthUserRepositoryRepository.findByoAuthUser(oAuthUser);
	}

	public Optional<OAuthUserRepositoryTable> getOAuthUserRepositoryTable(OAuthUser oAuthUser, Repository repository) {
		return oAuthUserRepositoryRepository.findByoAuthUserAndRepository(oAuthUser, repository);
	}
}
