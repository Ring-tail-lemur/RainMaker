package com.ringtaillemur.rainmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.PullRequest;
import com.ringtaillemur.rainmaker.repository.PullRequestRepository;
import com.ringtaillemur.rainmaker.util.exception.ConditionNotFoundException;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PullRequestService {

	private final PullRequestRepository pullRequestRepository;

	public PullRequest findById(Long id) {
		return pullRequestRepository.findById(id)
			.orElseThrow(() -> new ConditionNotFoundException(id + "를 id로 가진 PullRequest Entity가 없습니다."));
	}
}
