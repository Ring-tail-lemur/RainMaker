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
public class TimeToRestoreService extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "time_to_restore_service_id")
	private Long id;

	private Long restoreServiceTime;

	private Long releaseSuccessId;

	private LocalDateTime restoredAt;

	private Long repositoryId;
}
