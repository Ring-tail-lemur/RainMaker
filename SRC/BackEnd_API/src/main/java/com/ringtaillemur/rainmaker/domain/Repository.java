package com.ringtaillemur.rainmaker.domain;

import javax.persistence.*;

import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Repository extends BaseEntity {
	@Id
	@Column(name = "repository_id")
	private Long id;


	@Enumerated(EnumType.STRING)
	private OwnerType ownerType;

	private String name;

	private Long ownerOrganizationId;

	private Long ownerUserId;
}
