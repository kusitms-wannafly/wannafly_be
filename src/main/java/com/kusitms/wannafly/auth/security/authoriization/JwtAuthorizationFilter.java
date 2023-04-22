package com.kusitms.wannafly.auth.security.authoriization;

import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.AuthorizationRequest;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.jwt.JwtTokenExtractor;
import com.kusitms.wannafly.auth.security.Oauth2Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = JwtTokenExtractor.extract(request);
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(accessToken);
        AuthorizationResponse authorizationResponse = authService.authorize(authorizationRequest);

        if (authorizationResponse.isAuthorized()) {
            Oauth2Member oauth2Member = new Oauth2Member(authorizationResponse.memberId(), accessToken);
            configureSecurityContext(oauth2Member);
        }
        filterChain.doFilter(request, response);
    }

    private static void configureSecurityContext(Oauth2Member oauth2Member) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(oauth2Member, null);
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
