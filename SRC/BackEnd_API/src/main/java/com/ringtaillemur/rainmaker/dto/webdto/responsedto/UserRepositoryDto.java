package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserRepositoryDto {

	private int id;
	private String organization;
	private String repository;
	private LocalDateTime pushedAt;

	private boolean isChecked;

	public UserRepositoryDto(int id, String organization, String repository, LocalDateTime pushedAt,
		boolean isChecked) {
		this.id = id;
		this.organization = organization;
		this.repository = repository;
		this.pushedAt = pushedAt;
		this.isChecked = isChecked;
	}
}
