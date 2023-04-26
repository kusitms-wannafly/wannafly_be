package com.kusitms.wannafly.support.mockapi;

import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.security.Oauth2Member;
import com.kusitms.wannafly.auth.security.authentication.OAuthLoginSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MockAuthController {

    private static final String MOCK_NAME = "이동규";
    private static final String MOCK_EMAIL = "ldk@gmail.com";
    private static final String MOCK_PICTURE = "picture.com";

    private final OAuthLoginSuccessHandler successHandler;
    private final AuthService authService;

    public MockAuthController(OAuthLoginSuccessHandler successHandler, AuthService authService) {
        this.successHandler = successHandler;
        this.authService = authService;
    }

    @GetMapping("/mock/oauth2/authorization/google")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginResponse loginResponse = authService.login(new LoginRequest(MOCK_NAME, MOCK_EMAIL, MOCK_PICTURE));
        Authentication authentication = makeAuthentication(loginResponse);
        successHandler.onAuthenticationSuccess(request, response, authentication);
    }

    private Authentication makeAuthentication(LoginResponse loginResponse) {
        Oauth2Member oauth2Member = new Oauth2Member(loginResponse.memberId(), loginResponse.accessToken());
        return new UsernamePasswordAuthenticationToken(
                oauth2Member, null, oauth2Member.getAuthorities()
        );
    }
}
