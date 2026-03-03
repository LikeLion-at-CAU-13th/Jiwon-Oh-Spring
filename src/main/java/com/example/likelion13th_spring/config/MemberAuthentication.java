package com.example.likelion13th_spring.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {
    public MemberAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials);
    }
    public static MemberAuthentication createMemberAuthentication(String username) {
        return new MemberAuthentication(username, null, null);
    }
}
