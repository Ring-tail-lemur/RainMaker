package com.ringtaillemur.rainmaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ringtaillemur.rainmaker.repository.CodeVisitBranchesRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeVisitBranchesService {

	private final CodeVisitBranchesRepository codeVisitBranchesRepository;

}
