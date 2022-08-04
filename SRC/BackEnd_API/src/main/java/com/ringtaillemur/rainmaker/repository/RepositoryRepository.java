package com.ringtaillemur.rainmaker.repository;

import com.ringtaillemur.rainmaker.domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
