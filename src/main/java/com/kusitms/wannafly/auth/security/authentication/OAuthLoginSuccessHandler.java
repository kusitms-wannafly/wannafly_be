package com.kusitms.wannafly.auth.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.auth.security.oauth.OAuth2Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2Member oauth2Member = (OAuth2Member) authentication.getPrincipal();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(oauth2Member.toLoginResponse()));
        writer.flush();
    }
}
