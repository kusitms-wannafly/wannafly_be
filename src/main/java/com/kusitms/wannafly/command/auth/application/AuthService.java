package com.kusitms.wannafly.command.auth.application;

import com.kusitms.wannafly.command.auth.dto.*;
import com.kusitms.wannafly.command.auth.security.MemberOAuthRepository;
import com.kusitms.wannafly.command.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.command.auth.token.RefreshToken;
import com.kusitms.wannafly.command.auth.token.RefreshTokenProvider;
import com.kusitms.wannafly.command.auth.token.TokenPayload;
import com.kusitms.wannafly.command.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ReIssueResponse reIssueTokens(String refreshTokenValue) {
        RefreshToken newRefreshToken = refreshTokenProvider.reIssueToken(refreshTokenValue);
        Long memberId = newRefreshToken.getMemberId();
        String newAccessToken = jwtTokenProvider.createToken(new TokenPayload(memberId));
        return new ReIssueResponse(memberId, newAccessToken, newRefreshToken.getValue());
    }

    @Transactional
    public void logoutRefreshToken(String refreshTokenValue){
        refreshTokenProvider.deleteToken(refreshTokenValue);
    }
}
