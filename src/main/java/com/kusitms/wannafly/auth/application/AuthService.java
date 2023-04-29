package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.dto.AuthorizationRequest;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.security.MemberOAuthRepository;
import com.kusitms.wannafly.auth.token.*;
import com.kusitms.wannafly.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberOAuthRepository memberRepository;
    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Long memberId = getOrJoin(request);
        TokenPayload payload = new TokenPayload(memberId);
        String accessToken = tokenProvider.createToken(payload);
        String refreshToken = refreshTokenRepository.save(refreshTokenProvider.createToken(payload));
        return new LoginResponse(memberId, accessToken, refreshToken);
    }

    private Long getOrJoin(LoginRequest request) {
        return memberRepository.findByEmailAndRegistrationId(request.email(), request.registrationId())
                .orElseGet(() -> memberService.join(request.toMemberRequest()))
                .getId();
    }

    public AuthorizationResponse authorize(AuthorizationRequest request) {
        String accessToken = request.accessToken();
        if (tokenProvider.isValidAccessToken(accessToken)) {
            TokenPayload payload = tokenProvider.getPayload(accessToken);
            return AuthorizationResponse.authorized(payload.id());
        }
        return AuthorizationResponse.unauthorized();
    }
}
