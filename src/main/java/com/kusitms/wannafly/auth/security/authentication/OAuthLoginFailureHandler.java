package com.kusitms.wannafly.auth.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthLoginFailureHandler implements AuthenticationFailureHandler {

    @Value("${security.jwt.token.redirect-url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        OAuth2AuthenticationException oAuthException = (OAuth2AuthenticationException) exception;
        OAuth2Error oAuth2Error = oAuthException.getError();

        response.sendRedirect(redirectUrl + "/login-fail?errorCode=" + oAuth2Error.getErrorCode());
    }
}
