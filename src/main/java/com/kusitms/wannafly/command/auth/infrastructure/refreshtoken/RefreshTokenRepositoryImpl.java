package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken;

import com.kusitms.wannafly.command.auth.token.RefreshToken;
import com.kusitms.wannafly.command.auth.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository jpaRefreshTokenRepository;

    @Override
    @Transactional
    public void save(RefreshToken refreshToken) {
        JpaRefreshToken jpaRefreshToken = new JpaRefreshToken(refreshToken);
        jpaRefreshTokenRepository.save(jpaRefreshToken);
    }

    @Override
    public Optional<RefreshToken> findByValue(String refreshTokenValue) {
        return jpaRefreshTokenRepository.findById(refreshTokenValue)
                .map(JpaRefreshToken::toRefreshToken);
    }

    @Override
    @Transactional
    public void delete(RefreshToken refreshToken) {
        jpaRefreshTokenRepository.delete(new JpaRefreshToken(refreshToken));
    }
}
