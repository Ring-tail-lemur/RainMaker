package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRepoIdDto {

	@NotNull
	@Size(min = 1)
	List<String> repoIds;

	public RegisterRepoIdDto() {

	}
}
