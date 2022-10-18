package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepositoryInfoDto {
	private Long repositoryId;
	private String repositoryName;
}
