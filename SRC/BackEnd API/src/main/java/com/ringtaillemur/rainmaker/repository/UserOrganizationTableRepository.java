package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;

public interface UserOrganizationTableRepository extends JpaRepository<GitOrganization, Long> {
}
