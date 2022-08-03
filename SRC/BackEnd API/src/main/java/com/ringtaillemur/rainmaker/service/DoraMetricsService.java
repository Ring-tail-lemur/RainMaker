package com.ringtaillemur.rainmaker.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import com.ringtaillemur.rainmaker.domain.*;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.repository.DeploymentEventRepository;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;
import com.ringtaillemur.rainmaker.repository.PullRequestRepository;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DoraMetricsService {

    private final RepositoryRepository repositoryRepository;
    private final DeploymentEventRepository deploymentEventRepository;
    private final LeadTimeForChangeRepository leadTimeForChangeRepository;


    public LeadTimeForChangeByTimeDto getLeadTimeForChangeByTime(int repo_id, LocalDateTime start_time, LocalDateTime end_time) {

        LeadTimeForChangeByTimeDto dto = new LeadTimeForChangeByTimeDto();
        dto.setStart_time(start_time.toLocalDate());
        dto.setEnd_time(end_time.toLocalDate());
        dto.setLevel(ProductivityLevel.FRUIT);

        Repository repo = repositoryRepository.findById(1L).get();

        List<LeadTimeForChange> leadTimeForChangeList = leadTimeForChangeRepository.findByRepoIdAndTime(repo, start_time, end_time);
        Map<LocalDate, List<Integer>> AverageTimeMap = makeHashMap(start_time, end_time);

        for (LeadTimeForChange leadTimeForChange : leadTimeForChangeList) {
            LocalDate localDate = leadTimeForChange.getModifiedDate().toLocalDate();
            if(leadTimeForChange.getDeploymentTime() == null) continue;
            // DeploymentTime이 NULL이면 continue
            AverageTimeMap.get(localDate).add((int) (Duration.between(leadTimeForChange.getFirstCommitTime(),leadTimeForChange.getDeploymentTime()).getSeconds() / 3600));
        }

        System.out.println("AverageTimeMap===========" + AverageTimeMap);
        Map<LocalDate, Integer> leadTimeForChangeAverageMap = new HashMap<>();

        for(Map.Entry<LocalDate, List<Integer>> entry : AverageTimeMap.entrySet()) {
            LocalDate key = entry.getKey();
            List<Integer> value = entry.getValue();
            leadTimeForChangeAverageMap.put(key, (int) getAverage(value));
        }

        dto.setLeadTimeForChangeAverageMap(leadTimeForChangeAverageMap);
//		dto.setLeadTimeForChangeAverage(collect);

        return dto;
    }

    public DeploymentFrequencyDto getDeploymentFrequencyByTimeAndRepo(int repo_id, LocalDateTime start_time, LocalDateTime end_time) {
        DeploymentFrequencyDto deploymentFrequencyDto = new DeploymentFrequencyDto();
        deploymentFrequencyDto.setStart_time(start_time.toLocalDate());
        deploymentFrequencyDto.setEnd_time(end_time.toLocalDate());
        deploymentFrequencyDto.setLevel(ProductivityLevel.FRUIT);

        Repository repo = repositoryRepository.findById(1L).get();
        List<DeploymentEvent> deploymentEventList = deploymentEventRepository.findByRepoIdAndTime(repo, start_time, end_time);

        Map<LocalDate, Integer> AverageTimeMap = new HashMap<>();
        Duration totalTime = Duration.between(start_time, end_time);
        long leadTimeForChangeDays = totalTime.getSeconds() / 3600 / 24 + 1;
        for (int i = 0; i < leadTimeForChangeDays; i++) {
            AverageTimeMap.put(start_time.toLocalDate(), 0);
            start_time = start_time.plusDays(1);
        }


        for (DeploymentEvent deploymentEvent : deploymentEventList) {
            LocalDate localDate = deploymentEvent.getDeploymentSuccessTime().toLocalDate();
            AverageTimeMap.put(localDate,AverageTimeMap.get(localDate)+1);
        }

        deploymentFrequencyDto.setDeploymentFrequencyMap(AverageTimeMap);

        return deploymentFrequencyDto;
    }


    private Map<LocalDate, List<Integer>> makeHashMap(LocalDateTime start_time, LocalDateTime end_time) {
        Map<LocalDate, List<Integer>> AverageTimeMap = new HashMap<>();
        Duration totalTime = Duration.between(start_time, end_time);
        long leadTimeForChangeDays = totalTime.getSeconds() / 3600 / 24 + 1;
        for (int i = 0; i < leadTimeForChangeDays; i++) {
            AverageTimeMap.put(start_time.toLocalDate(), new ArrayList<Integer>());
            start_time = start_time.plusDays(1);
        }
        return AverageTimeMap;
    }

    private static double getAverage(List<Integer> list) {
        IntSummaryStatistics stats = list.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        return stats.getAverage();
    }

}
