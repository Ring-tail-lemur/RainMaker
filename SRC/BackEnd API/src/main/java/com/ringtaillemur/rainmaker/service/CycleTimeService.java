package com.ringtaillemur.rainmaker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ringtaillemur.rainmaker.repository.LeadTimeForChangeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import com.ringtaillemur.rainmaker.dto.domaindto.CycleTimeDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CycleTimeService {

	private final LeadTimeForChangeRepository leadTimeForChangeRepository;

	public void test() {
		// = leadTimeForChangeRepository.findById(1L);
	}
}
