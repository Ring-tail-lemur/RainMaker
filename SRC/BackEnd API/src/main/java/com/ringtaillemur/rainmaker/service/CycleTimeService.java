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


	public leadTimeForChangeByTimeDto getLeadTimeForChangeByTime(int repo_id, LocalDateTime start_time, LocalDateTime end_time) {

		Repository repo = repositoryRepository.findById(1L).get();
		System.out.println("===========" + start_time + end_time);
		List<LeadTimeForChange> leadTimeForChangeList = leadTimeForChangeRepository.findByRepoIdAndTime(repo, start_time, end_time);

		List<Integer> AverageTimeList = new ArrayList<>();
		Duration totalTime = Duration.between(start_time, end_time);
		long leadTimeForChangeDays = totalTime.getSeconds() / 3600 / 24 + 1;


//		int[] arr = new int[];
		for(LeadTimeForChange leadTimeForChange : leadTimeForChangeList) {

			for(int i = 0; i <leadTimeForChangeDays; i++) {
				LocalDateTime localDateTime = start_time.plusDays(i);

				if(leadTimeForChange.getModifiedDate().compareTo(localDateTime)
				&& ) {

				}
			}

			System.out.println("LEADTIME : ================ " + leadTimeForChange.getDeploymentTime());
/*			if(  leadTimeForChange.getModifiedDate().compareTo(start_time) == 1
					&& leadTimeForChange.getModifiedDate().compareTo(end_time) == -1) {
				// 시간안에 들어있다면,
				if(start_time )
				LocalDateTime firstCommitTime = leadTimeForChange.getFirstCommitTime();
				LocalDateTime deploymentTime = leadTimeForChange.getDeploymentTime();

				Duration totalTime = Duration.between(firstCommitTime, deploymentTime);
				long leadTimeForChangeHours = totalTime.getSeconds() / 3600;
				AverageTimeList.add((int) leadTimeForChangeHours);
			} */

		}

		AverageTimeList.add(10);
		for(Integer timehours : AverageTimeList) {

		}

		leadTimeForChangeByTimeDto dto = new leadTimeForChangeByTimeDto();
		dto.setStart_time(start_time);
		dto.setEnd_time(end_time);
		dto.setLevel(ProductivityLevel.FRUIT);
		dto.setLeadTimeForChangeAverage(AverageTimeList);

		return dto;
	}

}
