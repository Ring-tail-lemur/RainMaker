package com.ringtaillemur.analyst.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseDto {
	private String repositoryId;
	private String tagName;
	private String repositoryName;
	private String ownerName;
	private String releaseId;
}
