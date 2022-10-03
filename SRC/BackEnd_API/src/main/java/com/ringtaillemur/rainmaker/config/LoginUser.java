package com.ringtaillemur.rainmaker.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.ringtaillemur.rainmaker.domain.OAuthUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser extends User {
	private OAuthUser oAuthUser;

	public LoginUser(OAuthUser oAuthUser){
		super(oAuthUser.getUserRemoteId().toString(), oAuthUser.getName(), AuthorityUtils.createAuthorityList(oAuthUser.getUserLevel().toString()));
		this.oAuthUser = oAuthUser;
	}
}
