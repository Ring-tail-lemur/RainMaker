package com.ringtaillemur.rainmaker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetail;
import com.ringtaillemur.rainmaker.domain.view.LeadTimeForChangeSourcePullRequestDetailData;

public interface LeadTimeForChangeSourcePullRequestDetailRepository extends
	JpaRepository<LeadTimeForChangeSourcePullRequestDetail, LeadTimeForChangeSourcePullRequestDetailData> {

	List<LeadTimeForChangeSourcePullRequestDetail> findByDataLeadTimeForChangeIdIn(List<Long> leadTimeForChangeIdList);
}
