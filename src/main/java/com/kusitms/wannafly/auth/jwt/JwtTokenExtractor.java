package com.kusitms.wannafly.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class JwtTokenExtractor {

    private static final String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateBearerToken(token);
        return token.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateBearerToken(String bearerToken) {
        if (bearerToken == null
                || !bearerToken.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new RuntimeException("올바른 형식의 토큰 헤더가 아닙니다.");
        }
    }
}
