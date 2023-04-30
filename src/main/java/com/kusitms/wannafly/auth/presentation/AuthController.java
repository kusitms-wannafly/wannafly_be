package com.kusitms.wannafly.auth.presentation;

import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.ReIssueResponse;
import com.kusitms.wannafly.auth.token.RefreshTokenSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/accessToken")
    public ResponseEntity<ReIssueResponse> reIssueToken(@CookieValue(name = "refreshToken") String refreshToken) {
        ReIssueResponse response = authService.reIssueTokens(refreshToken);
        ResponseCookie cookie = RefreshTokenSupport.convertToCookie(response.refreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}
