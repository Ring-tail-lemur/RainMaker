package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Release extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "release_id")
	private Long id;

	private Boolean preRelease;
	private String name;
	private LocalDateTime publishedAt;
	private Boolean draft;
	private Boolean leadTimeForChangeProcessEnd;
	private Boolean changeFailureRateProcessEnd;

	private Long authorId;

	private Long tagId;

	private Long repositoryId;
}
