package com.kusitms.wannafly.auth.security.authentication;

import com.kusitms.wannafly.auth.security.oauth.OAuth2Member;
import com.kusitms.wannafly.auth.token.RefreshTokenSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String SET_COOKIE = "Set-Cookie";

    @Value("${security.redirect-url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2Member oauth2Member = (OAuth2Member) authentication.getPrincipal();
        ResponseCookie cookie = RefreshTokenSupport.convertToCookie(oauth2Member.getRefreshToken());

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader(SET_COOKIE, cookie.toString());
        response.sendRedirect(redirectUrl + "/token?accessToken=" + oauth2Member.getAccessToken());
    }
}
