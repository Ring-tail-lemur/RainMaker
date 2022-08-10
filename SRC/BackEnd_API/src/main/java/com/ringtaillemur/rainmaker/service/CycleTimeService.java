package com.ringtaillemur.rainmaker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.PullRequest;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;
import com.ringtaillemur.rainmaker.repository.PullRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CycleTimeService {

	private final PullRequestRepository pullRequestRepository;

}
