package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.LeadTimeForChange;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChange, Long> {

}
