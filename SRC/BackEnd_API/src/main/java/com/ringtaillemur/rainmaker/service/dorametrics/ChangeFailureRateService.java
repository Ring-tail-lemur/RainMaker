package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.Repository;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;
import com.ringtaillemur.rainmaker.repository.FailedChangeRepository;
import com.ringtaillemur.rainmaker.repository.RepositoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeFailureRateService {

	private final FailedChangeRepository failedChangeRepository;
	private final RepositoryRepository repositoryRepository;

	public ChangeFailureRateDto getChangeFailureRate(Long repositoryId, LocalDate startTime, LocalDate endTime) {
		ChangeFailureRateDto changeFailureRateDto = ChangeFailureRateDto.builder()
			.startTime(startTime)
			.endTime(endTime)
			.build();
		Repository repository = repositoryRepository.findById(repositoryId)
			.orElseThrow(() -> new NullPointerException("there is no repository which have this id"));
		failedChangeRepository
	}
}
