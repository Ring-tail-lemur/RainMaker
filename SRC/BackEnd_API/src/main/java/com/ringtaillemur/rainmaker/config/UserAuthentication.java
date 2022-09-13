package com.ringtaillemur.rainmaker.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(String principal, String credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(String principal, String credentials,
                              List<GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}