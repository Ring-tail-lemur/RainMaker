package com.ringtaillemur.rainmaker.domain;

import java.time.LocalDateTime;

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
public class TimeToRestoreService extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "time_to_restore_service_id")
	private Long id;

	private Long restoreServiceTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "failed_change_id")
	private FailedChange failedChange;

	private LocalDateTime restoredAt;
 }
