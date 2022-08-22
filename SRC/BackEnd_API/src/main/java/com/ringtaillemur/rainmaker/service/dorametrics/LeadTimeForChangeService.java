package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeadTimeForChangeService {

    private final LeadTimeForChangeRepository leadTimeForChangeRepository;
    private final RepositoryRepository repositoryRepository;

    public LeadTimeForChangeByTimeDto getLeadTimeForChangeByTime(Long repo_id, LocalDate startTime, LocalDate endTime) {
        LeadTimeForChangeByTimeDto dto = LeadTimeForChangeByTimeDto.builder()
                .startTime(startTime)
                .endTime(endTime)
                .build();

        Repository repo = repositoryRepository.findById(repo_id)
                .orElseThrow(() -> new NullPointerException("there is no repository which have this id"));
        LocalDateTime start_time = startTime.atStartOfDay();
        LocalDateTime end_time = endTime.plusDays(1).atStartOfDay();
        List<LeadTimeForChange> leadTimeForChangeList = leadTimeForChangeRepository.findByRepoIdAndTime(repo,
                start_time, end_time);
        Map<LocalDate, List<Integer>> AverageTimeMap = makeHashMap(startTime, endTime);

        for (LeadTimeForChange leadTimeForChange : leadTimeForChangeList) {
            LocalDate localDate = leadTimeForChange.getDeploymentTime().toLocalDate();
            if (leadTimeForChange.getDeploymentTime() == null)
                continue;
            AverageTimeMap.get(localDate).add((int) (
                    Duration.between(leadTimeForChange.getFirstCommitTime(), leadTimeForChange.getDeploymentTime())
                            .getSeconds() / 3600));
        }

        Map<LocalDate, Integer> leadTimeForChangeAverageMap = new HashMap<>();

        for (Map.Entry<LocalDate, List<Integer>> entry : AverageTimeMap.entrySet()) {
            LocalDate key = entry.getKey();
            List<Integer> value = entry.getValue();
            leadTimeForChangeAverageMap.put(key, (int) getAverage(value));
        }

        dto.setLeadTimeForChangeAverageMap(leadTimeForChangeAverageMap);
        dto.setLevel();

        return dto;
    }

    private Map<LocalDate, List<Integer>> makeHashMap(LocalDate startTime, LocalDate endTime) {
        Map<LocalDate, List<Integer>> AverageTimeMap = new HashMap<>();
        long leadTimeForChangeDays = ChronoUnit.DAYS.between(startTime, endTime) + 1;
        for (int i = 0; i < leadTimeForChangeDays; i++) {
            AverageTimeMap.put(startTime, new ArrayList<>());
            startTime = startTime.plusDays(1);
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
