package com.rainmaker.rainmakerwebserver.domain.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.meta.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
