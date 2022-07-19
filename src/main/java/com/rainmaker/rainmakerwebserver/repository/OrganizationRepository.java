package com.rainmaker.rainmakerwebserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainmaker.rainmakerwebserver.domain.jpadomain.generic.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
