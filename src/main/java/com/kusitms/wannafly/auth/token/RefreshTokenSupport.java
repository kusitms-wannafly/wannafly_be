package com.kusitms.wannafly.auth.token;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenSupport {

    private static final String REFRESH_TOKEN = "refreshToken";

    public static ResponseCookie convertToCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(SameSite.NONE.attributeValue())
                .build();
    }

    public static Cookie extractFrom(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN_IN_COOKIE));
    }
}
