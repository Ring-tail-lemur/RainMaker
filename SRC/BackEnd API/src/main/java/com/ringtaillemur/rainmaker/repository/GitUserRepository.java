package com.ringtaillemur.rainmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.GitUser;

public interface GitUserRepository extends JpaRepository<GitUser, Long> {
}
