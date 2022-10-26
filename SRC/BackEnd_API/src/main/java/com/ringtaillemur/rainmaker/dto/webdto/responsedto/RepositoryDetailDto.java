package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.Builder;
import lombok.Data;

@Data
public class RepositoryDetailDto {

	Long repositoryId;
	String organizationName;
	String repositoryName;

	@Builder
	public RepositoryDetailDto(Long repositoryId, String organizationName, String repositoryName) {
		this.repositoryId = repositoryId;
		this.organizationName = organizationName;
		this.repositoryName = repositoryName;
	}

}
