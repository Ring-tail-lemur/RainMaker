package com.ringtaillemur.analyst.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
@Getter
public class BaseEntity {
    @Column(insertable=false, updatable=false)
	private LocalDateTime createdDate;


    @Column(insertable=false, updatable=false)
	private LocalDateTime modifiedDate;
}
