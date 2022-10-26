package com.ringtaillemur.rainmaker.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.repository.GitOrganizationRepository;
import com.ringtaillemur.rainmaker.util.exception.ConditionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GitOrganizationService {

	private final GitOrganizationRepository gitOrganizationRepository;

	public void saveGitOrganizations(Set<GitOrganization> gitOrganizationList) {
		gitOrganizationRepository.saveAll(gitOrganizationList);
	}

	public GitOrganization findGitOrganizationById(Long id) {
		return gitOrganizationRepository.findById(id).orElseThrow(()-> new ConditionNotFoundException(id + "의 Organzation이 존재하지 않습니다."));
	}
}
