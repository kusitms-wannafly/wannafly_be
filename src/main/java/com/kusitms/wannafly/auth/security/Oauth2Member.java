package com.kusitms.wannafly.auth.security;

import com.kusitms.wannafly.auth.dto.LoginResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Oauth2Member implements OAuth2User {

    private final Map<String, Object> attributes = new HashMap<>();
    private final LoginResponse loginResponse;

    public Oauth2Member(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return String.valueOf(loginResponse.memberId());
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
