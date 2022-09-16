package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterRepoIdDto {
    List<String> repoIds;

    public RegisterRepoIdDto() {

    }
}
