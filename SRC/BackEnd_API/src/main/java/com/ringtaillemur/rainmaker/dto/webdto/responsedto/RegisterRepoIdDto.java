package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegisterRepoIdDto {

    @NotNull
    List<String> repoIds;

    public RegisterRepoIdDto() {

    }
}
