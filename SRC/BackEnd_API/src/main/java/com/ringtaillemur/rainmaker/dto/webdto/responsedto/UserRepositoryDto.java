package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRepositoryDto {

    private int id;
    private String organization;
    private String repository;
    private LocalDateTime pushed_at;

    private boolean isChecked;
    public UserRepositoryDto(int id, String organization, String repository, LocalDateTime pushed_at, boolean isChecked) {
        this.id = id;
        this.organization = organization;
        this.repository = repository;
        this.pushed_at = pushed_at;
        this.isChecked = isChecked;
    }
}
