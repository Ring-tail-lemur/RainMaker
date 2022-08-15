package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.domain.WorkflowRun;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.repository.WorkflowRunRepository;
import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeploymentFrequencyService {

	private final RepositoryRepository repositoryRepository;
	private final WorkflowRunRepository workflowRunRepository;

	public DeploymentFrequencyDto getDeploymentFrequency(Long repo_id, LocalDate startTime, LocalDate endTime) {
		DeploymentFrequencyDto deploymentFrequencyDto = DeploymentFrequencyDto.builder()
			.start_time(startTime)
			.end_time(endTime)
			.level(ProductivityLevel.FRUIT)
			.build();

		Repository repo = repositoryRepository.findById(repo_id).get();
		List<WorkflowRun> workflowRunList = workflowRunRepository.findByRepoIdAndTime(repo, startTime.atStartOfDay(),
			endTime.plusDays(1).atStartOfDay());

		Map<LocalDate, Integer> AverageTimeMap = new HashMap<>();
		long leadTimeForChangeDays = ChronoUnit.DAYS.between(startTime, endTime);

		for (int i = 0; i < leadTimeForChangeDays; i++) {
			AverageTimeMap.put(startTime, 0);
			startTime = startTime.plusDays(1);
		}

		for (WorkflowRun workflowRun : workflowRunList) {
			LocalDate localDate = workflowRun.getWorkflowEndTime().toLocalDate();
			AverageTimeMap.put(localDate, AverageTimeMap.get(localDate) + 1);
		}

		deploymentFrequencyDto.setDeploymentFrequencyMap(AverageTimeMap);
		return deploymentFrequencyDto;
	}
}
