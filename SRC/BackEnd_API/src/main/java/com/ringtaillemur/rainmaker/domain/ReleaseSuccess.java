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
public class ReleaseSuccess extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "release_success_id")
	private Long id;

	private Boolean timeToRestoreServiceProcessEnd;

	private Long releaseId;

	private Long firstErrorIssueId;

	private Long repositoryId;

	private Boolean isSuccess;

	private LocalDateTime failedAt;

	private LocalDateTime releasedAt;

	public Long getIsSuccessLongValue() {
		if (Boolean.TRUE.equals(isSuccess)) {
			return 0L;
		}
		return 1L;
	}
}
