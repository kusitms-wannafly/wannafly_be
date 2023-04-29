package com.kusitms.wannafly.auth.infrastructure.refreshtoken;

import com.kusitms.wannafly.auth.token.RefreshToken;
import com.kusitms.wannafly.auth.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    @Override
    public String save(RefreshToken refreshToken) {
        JpaRefreshToken jpaRefreshToken = new JpaRefreshToken(refreshToken);
        jpaRefreshTokenRepository.save(jpaRefreshToken);
        return refreshToken.getValue();
    }
}
