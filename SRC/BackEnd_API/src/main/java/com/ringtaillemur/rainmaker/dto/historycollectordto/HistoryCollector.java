package com.ringtaillemur.rainmaker.dto.historycollectordto;

import lombok.Builder;
import lombok.Data;

@Data
public class HistoryCollector {
	private String owner;
	private String repo;
	private String token;

	@Builder
	public HistoryCollector(String ownerName, String repoName, String token) {
		this.owner = ownerName;
		this.repo = repoName;
		this.token = token;
	}
}
