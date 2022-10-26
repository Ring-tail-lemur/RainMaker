package com.ringtaillemur.rainmaker.dto.historycollectordto;

import lombok.Builder;
import lombok.Data;

@Data
public class HistoryCollectorDto {
	private String owner;
	private String repo;
	private String token;

	@Builder
	public HistoryCollectorDto(String ownerName, String repoName, String token) {
		this.owner = ownerName;
		this.repo = repoName;
		this.token = token;
	}
}
