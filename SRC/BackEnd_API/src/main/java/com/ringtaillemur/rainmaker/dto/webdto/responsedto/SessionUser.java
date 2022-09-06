package com.ringtaillemur.rainmaker.dto.webdto.responsedto;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import lombok.Getter;

@Getter
public class SessionUser {
    private Long id;
    private String name;

    public SessionUser(OAuthUser oAuthUser){
        this.id = oAuthUser.getId();
        this.name = oAuthUser.getName();
    }
}
