package com.kusitms.wannafly.auth.presentation;

import com.kusitms.wannafly.auth.token.RefreshToken;
import com.kusitms.wannafly.auth.token.RefreshTokenRepository;
import com.kusitms.wannafly.auth.token.RefreshTokenSupport;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RefreshTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == RefreshToken.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Cookie refreshTokenCookie = RefreshTokenSupport.extractFrom(Objects.requireNonNull(request));
        String refreshTokenValue = refreshTokenCookie.getValue();
        return refreshTokenRepository.findByValue(refreshTokenValue)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY));
    }
}
