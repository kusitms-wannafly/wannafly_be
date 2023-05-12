package com.kusitms.wannafly.auth.security.authoriization;

import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.AuthorizationRequest;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.security.oauth.OAuth2Member;
import com.kusitms.wannafly.auth.token.JwtSupport;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> extractToken = JwtSupport.extractToken(request);
        extractToken.ifPresent(this::validateToken);
        filterChain.doFilter(request, response);
    }

    private void validateToken(String accessToken) {
        AuthorizationRequest authorizationRequest = new AuthorizationRequest(accessToken);
        AuthorizationResponse authorizationResponse = authService.authorize(authorizationRequest);

        if (authorizationResponse.isAuthorized()) {
            OAuth2Member oauth2Member = new OAuth2Member(authorizationResponse.memberId(), accessToken);
            configureSecurityContext(oauth2Member);
        }
    }

    private static void configureSecurityContext(OAuth2Member oauth2Member) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                oauth2Member, null, oauth2Member.getAuthorities()
        );
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
