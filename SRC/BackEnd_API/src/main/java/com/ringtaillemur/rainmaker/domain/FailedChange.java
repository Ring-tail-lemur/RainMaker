package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FailedChange extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "failed_change_id")
	private Long id;

	private Long timeToRestoreServiceProcessEnd;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "release_id")
	private Release release;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "first_error_issue_id")
	private Issue firstErrorIssue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;

	private LocalDateTime failedAt;
}
