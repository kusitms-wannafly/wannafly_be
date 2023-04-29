package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.dto.*;
import com.kusitms.wannafly.auth.security.MemberOAuthRepository;
import com.kusitms.wannafly.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.auth.token.RefreshToken;
import com.kusitms.wannafly.auth.token.RefreshTokenProvider;
import com.kusitms.wannafly.auth.token.TokenPayload;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberOAuthRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Long memberId = getOrJoin(request);
        TokenPayload payload = new TokenPayload(memberId);
        String accessToken = jwtTokenProvider.createToken(payload);
        RefreshToken refreshToken = refreshTokenProvider.createToken(payload);
        return new LoginResponse(memberId, accessToken, refreshToken.getValue());
    }

    private Long getOrJoin(LoginRequest request) {
        return memberRepository.findByEmailAndRegistrationId(request.email(), request.registrationId())
                .orElseGet(() -> memberService.join(request.toMemberRequest()))
                .getId();
    }

    public AuthorizationResponse authorize(AuthorizationRequest request) {
        String accessToken = request.accessToken();
        if (jwtTokenProvider.isValidAccessToken(accessToken)) {
            TokenPayload payload = jwtTokenProvider.getPayload(accessToken);
            return AuthorizationResponse.authorized(payload.id());
        }
        return AuthorizationResponse.unauthorized();
    }

    @Transactional
    public ReIssueResponse reIssueTokens(RefreshToken refreshToken) {
        validateRefreshTokenExpired(refreshToken);
        Long memberId = refreshToken.getMemberId();
        String newAccessToken = jwtTokenProvider.createToken(new TokenPayload(memberId));
        RefreshToken newRefreshToken = refreshTokenProvider.reIssueToken(refreshToken);
        return new ReIssueResponse(memberId, newAccessToken, newRefreshToken.getValue());
    }

    private void validateRefreshTokenExpired(RefreshToken refreshToken) {
        if (!refreshToken.isValid(LocalDateTime.now())) {
            throw BusinessException.from(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }
}
