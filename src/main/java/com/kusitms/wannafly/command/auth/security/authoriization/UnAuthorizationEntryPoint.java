package com.kusitms.wannafly.command.auth.security.authoriization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.exception.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class UnAuthorizationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorCode errorCode = ErrorCode.AUTHORIZATION_FAIL;
        response.setStatus(errorCode.getHttpStatusCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ExceptionResponse errorResponse = new ExceptionResponse(errorCode.getValue(), errorCode.getMessage());
        writer.println(objectMapper.writeValueAsString(errorResponse));
        writer.flush();
    }
}
