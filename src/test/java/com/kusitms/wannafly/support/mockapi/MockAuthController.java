package com.kusitms.wannafly.support.mockapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.command.auth.application.AuthService;
import com.kusitms.wannafly.command.auth.dto.LoginRequest;
import com.kusitms.wannafly.command.auth.dto.LoginResponse;
import com.kusitms.wannafly.command.auth.token.RefreshTokenSupport;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class MockAuthController {

    private static final String MOCK_NAME = "이동규";
    private static final String MOCK_EMAIL = "ldk@gmail.com";
    private static final String MOCK_PICTURE = "picture.com";

    private final AuthService authService;

    private final ObjectMapper objectMapper;

    public MockAuthController(AuthService authService, ObjectMapper objectMapper) {
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/mock/oauth2/authorization/{registrationId}")
    public void login(@PathVariable String registrationId,
                      HttpServletResponse response) throws IOException {
        LoginRequest loginRequest = new LoginRequest(registrationId, MOCK_NAME, MOCK_EMAIL, MOCK_PICTURE);
        LoginResponse loginResponse = authService.login(loginRequest);

        ResponseCookie cookie = RefreshTokenSupport.convertToCookie(loginResponse.refreshToken());
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(loginResponse));
        writer.flush();
    }
}
