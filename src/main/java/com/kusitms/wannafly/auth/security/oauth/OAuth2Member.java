package com.kusitms.wannafly.auth.security.oauth;

import com.kusitms.wannafly.auth.dto.LoginResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OAuth2Member implements OAuth2User {

    private final Map<String, Object> attributes = new HashMap<>();
    private final Long memberId;
    private final String accessToken;

    public OAuth2Member(Long memberId, String accessToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
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
        return String.valueOf(memberId);
    }

    public LoginResponse toLoginResponse() {
        return new LoginResponse(memberId, accessToken);
    }
}
