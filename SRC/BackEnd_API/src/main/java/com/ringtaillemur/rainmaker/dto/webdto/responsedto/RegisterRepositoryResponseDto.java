package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRepositoryResponseDto {

	@NotNull
	@Size(min = 1)
	private List<String> repoIds;

	//[[repo_id, ower_name, repo_name]
	private List<RepositoryDetailDto> repoDetailsList;

	public void setRepoIds(List<String> repoIds) {
		this.repoIds = repoIds;
		repoDetailsList = repoIds.stream()
			.map(repoInfos -> {
				String[] split = repoInfos.split(",");
				return RepositoryDetailDto.builder()
					.repositoryId(Long.valueOf(split[0]))
					.organizationName(split[1])
					.repositoryName(split[2])
					.build();
			})
			.toList();
	}
}
