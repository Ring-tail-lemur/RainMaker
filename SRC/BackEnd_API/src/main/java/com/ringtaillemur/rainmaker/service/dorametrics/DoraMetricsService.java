package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.ringtaillemur.rainmaker.domain.*;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.DeploymentFrequencyDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.LeadTimeForChangeByTimeDto;
import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;
import com.ringtaillemur.rainmaker.repository.WorkflowRunRepository;
import com.ringtaillemur.rainmaker.util.enumtype.ProductivityLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DoraMetricsService {

    private final RepositoryRepository repositoryRepository;

}
