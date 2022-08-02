package com.ringtaillemur.rainmaker.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.PullRequest;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.leadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;
import com.ringtaillemur.rainmaker.repository.PullRequestRepository;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CycleTimeService {

	private final RepositoryRepository repositoryRepository;
	private final LeadTimeForChangeRepository leadTimeForChangeRepository;
	private final EntityManagerFactory entityManagerFactory;


	public leadTimeForChangeByTimeDto getLeadTimeForChangeByTime(LocalDateTime start_time, LocalDateTime end_time) {


		Optional<Repository> targetRepository = repositoryRepository.findById(1L);
		List<LeadTimeForChange> leadTimeForChangeList = targetRepository.get().getLeadTimeForChangeList();
		// todo 문제. 여기서 WHERE문을 사용하지 않고 가져오게 되면 전부 다가져오게 됨.

		List<Integer> AverageTimeList = new ArrayList<>();
		for(LeadTimeForChange leadTimeForChange : leadTimeForChangeList) {
			// todo modified_time 기준으로 처리하였음. 더좋은 방향이 있을까
			if(  leadTimeForChange.getModifiedDate().compareTo(start_time) == 1
					&& leadTimeForChange.getModifiedDate().compareTo(end_time) == -1) {

				LocalDateTime firstCommitTime = leadTimeForChange.getFirstCommitTime();
				LocalDateTime firstReviewTime = leadTimeForChange.getFirstReviewTime();
				LocalDateTime Time = leadTimeForChange.getFirstReviewTime();
				LocalDateTime prCloseTime = leadTimeForChange.getPrCloseTime();
				LocalDateTime deploymentTime = leadTimeForChange.getDeploymentTime();
				System.out.println(firstCommitTime);
				System.out.println(firstReviewTime);

				Duration coding_time = Duration.between(firstCommitTime, firstReviewTime);
				Duration pickUp_time = Duration.between(firstReviewTime, firstReviewTime);
				Duration review_time = Duration.between(firstCommitTime, firstReviewTime);
				Duration deployment_time = Duration.between(firstCommitTime, firstReviewTime);

				Duration totalTime = Duration.between(firstCommitTime, deploymentTime);
				long leadTimeForChangeHours = totalTime.getSeconds() / 3600;
				AverageTimeList.add((int) leadTimeForChangeHours);
			}
		}

		LocalDateTime now = LocalDateTime.now();
		List<Integer> list = new ArrayList<>();
		list.add(24);

		leadTimeForChangeByTimeDto dto = new leadTimeForChangeByTimeDto();
		dto.setStart_time(now);
		dto.setEnd_time(now);
		dto.setLevel(ProductivityLevel.FRUIT);
		dto.setLeadTimeForChangeAverage(list);

		return dto;
	}

}
