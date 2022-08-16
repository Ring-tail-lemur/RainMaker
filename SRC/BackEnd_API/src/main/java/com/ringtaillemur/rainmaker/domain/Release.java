package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	private Long remoteIdentifier;
	private Boolean preRelease;
	private String name;
	private LocalDateTime publishedAt;
	private Boolean draft;
	private Boolean leadTimeForChangeProcessEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private GitUser author;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Branch tag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "release")
	private FailedChange failedChange;

	@OneToMany(mappedBy = "release")
	private List<ReleaseEvent> releaseEventList = new ArrayList<>();
}
