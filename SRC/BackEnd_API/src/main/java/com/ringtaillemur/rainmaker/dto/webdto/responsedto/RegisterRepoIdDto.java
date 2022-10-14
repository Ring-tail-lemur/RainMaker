package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class RegisterRepoIdDto {

    @NotNull
    @Size(min = 1)
    List<String> repoIds;

    public RegisterRepoIdDto() {

    }
}
