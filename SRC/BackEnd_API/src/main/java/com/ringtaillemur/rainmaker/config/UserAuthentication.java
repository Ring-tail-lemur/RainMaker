package com.ringtaillemur.rainmaker.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(String principal, String credentials) {
        super(principal, credentials);
    }
    public UserAuthentication(String principal, String credentials,
                              Set<SimpleGrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}