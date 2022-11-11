package com.ringtaillemur.rainmaker.domain.view;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MttrDetail {

	@EmbeddedId
	private MttrDetailData data;

}
