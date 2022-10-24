package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GitUser extends BaseEntity {
	@Id
	@Column(name = "git_user_id")
	private Long id;
	private String name;
}
