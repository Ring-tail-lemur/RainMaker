package com.rainmaker.rainmakerwebserver.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity{
    @CreatedDate
    private LocalDateTime creatTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
