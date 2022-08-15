package com.ringtaillemur.rainmaker.service.dorametrics;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.ChangeFailureRateDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeFailureRateService {

	// private final

	public ChangeFailureRateDto getChangeFailureRate(Long repositoryId, LocalDate startTime, LocalDate endTime) {
		return new ChangeFailureRateDto();
	}
}
