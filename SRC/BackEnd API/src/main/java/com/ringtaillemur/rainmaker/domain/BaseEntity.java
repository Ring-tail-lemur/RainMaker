package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class BaseEntity {
	@CreatedDate
	private LocalDateTime createTime;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
