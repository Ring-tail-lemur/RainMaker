package com.ringtaillemur.rainmaker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetail;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeSourcePullRequestDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeadTimeForChangeSourcePulRequestDetailService {

	private final LeadTimeForChangeSourcePullRequestDetailRepository leadTimeForChangeSourcePullRequestDetailRepository;

	public List<LeadTimeForChangeSourcePullRequestDetail> findByLeadTimeForChangeIdList(List<LeadTimeForChange> leadTimeForChangeList) {
		List<Long> leadTimeForChangeIdList = leadTimeForChangeList.stream().map(LeadTimeForChange::getId).toList();
		return leadTimeForChangeSourcePullRequestDetailRepository.findByDataLeadTimeForChangeIdIn(leadTimeForChangeIdList);
	}
}
