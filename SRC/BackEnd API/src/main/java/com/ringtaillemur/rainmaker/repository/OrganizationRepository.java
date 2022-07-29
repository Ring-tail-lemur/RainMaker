package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.jpadomain.generic.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
