package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.jwt.TokenPayload;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.jwt.JwtTokenProvider;
import com.kusitms.wannafly.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Long memberId = memberRepository.findByEmail(request.email())
                .orElseGet(() -> memberRepository.save(request.toMember()))
                .getId();
        String accessToken = tokenProvider.createToken(new TokenPayload(memberId));
        return new LoginResponse(memberId, accessToken);
    }
}
