package com.kusitms.wannafly.auth.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.auth.security.oauth.OAuth2Member;
import com.kusitms.wannafly.auth.token.RefreshTokenSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String SET_COOKIE = "Set-Cookie";

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2Member oauth2Member = (OAuth2Member) authentication.getPrincipal();
        response.setContentType("application/json;charset=UTF-8");

        String refreshToken = oauth2Member.getRefreshToken();
        ResponseCookie cookie = RefreshTokenSupport.convertToCookie(refreshToken);
        response.addHeader(SET_COOKIE, cookie.toString());

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(oauth2Member.toLoginResponse()));
        writer.flush();
    }
}
