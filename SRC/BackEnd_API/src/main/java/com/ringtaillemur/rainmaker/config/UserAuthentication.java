package com.ringtaillemur.rainmaker.config;

import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

	public UserAuthentication(String principal, String credentials) {
		super(principal, credentials);
	}

	public UserAuthentication(String principal, String credentials,
		Set<SimpleGrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

}
