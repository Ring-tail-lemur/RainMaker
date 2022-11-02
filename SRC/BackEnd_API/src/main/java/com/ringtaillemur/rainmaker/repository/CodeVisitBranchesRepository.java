package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.view.CodeVisitBranches;
import com.ringtaillemur.rainmaker.domain.view.CodeVisitBranchesData;

public interface CodeVisitBranchesRepository extends JpaRepository<CodeVisitBranches, CodeVisitBranchesData> {
}
