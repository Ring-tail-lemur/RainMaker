package com.ringtaillemur.analyst.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseDto {
	private String repository_id;
	private String tag_name;
	private String repository_name;
	private String owner_name;
	private String release_id;
}
