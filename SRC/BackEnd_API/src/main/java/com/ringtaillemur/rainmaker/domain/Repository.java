package com.ringtaillemur.rainmaker.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "repository")
	private List<OAuthUserRepositoryTable> oAuthUserRepositoryTableList = new ArrayList<>();
}
