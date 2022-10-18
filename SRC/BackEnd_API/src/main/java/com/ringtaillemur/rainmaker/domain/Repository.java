package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ringtaillemur.rainmaker.domain.enumtype.OwnerType;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RepositoryInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

	public RepositoryInfoDto getRepositoryInfoDto() {
		return new RepositoryInfoDto(this.id, this.name);
	}
}
