package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@CreatedDate
	private LocalDateTime createTime;

	@LastModifiedDate
	private LocalDateTime modifiedDate;


	@Autowired
	EntityManager entityManager;
}
