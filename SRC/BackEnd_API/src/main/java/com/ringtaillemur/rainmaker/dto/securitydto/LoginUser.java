package com.ringtaillemur.rainmaker.dto.securitydto;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.ringtaillemur.rainmaker.domain.OAuthUser;
import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser extends User {
	private Long userRemoteId;
	private OauthUserLevel userLevel;

	public LoginUser(OAuthUser oAuthUser){
		super(oAuthUser.getUserRemoteId().toString(), oAuthUser.getName(), AuthorityUtils.createAuthorityList(oAuthUser.getUserLevel().toString()));
		this.userRemoteId = oAuthUser.getUserRemoteId();
		this.userLevel = oAuthUser.getUserLevel();
	}
}
