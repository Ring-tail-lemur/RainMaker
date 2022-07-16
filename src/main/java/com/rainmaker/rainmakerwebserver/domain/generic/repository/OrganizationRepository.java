package com.rainmaker.rainmakerwebserver.domain.generic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.generic.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
