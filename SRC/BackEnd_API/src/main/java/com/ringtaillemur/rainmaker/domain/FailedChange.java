package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	@JoinColumn(name= "first_error_issue_id")
	private Issue firstErrorIssue;
}
