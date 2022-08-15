package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ringtaillemur.rainmaker.domain.enumtype.ReleaseEventType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReleaseEvent extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "release_event_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private ReleaseEventType releaseEventType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "release_id")
	private Release release;
}
