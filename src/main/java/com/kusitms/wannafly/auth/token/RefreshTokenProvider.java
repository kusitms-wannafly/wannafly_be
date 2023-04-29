package com.kusitms.wannafly.auth.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class RefreshTokenProvider {

    private static final String EXPIRE_LENGTH = "${security.refresh.token.expire-day}";

    private final long validityInDays;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenProvider(@Value(EXPIRE_LENGTH) long validityInDays,
                                RefreshTokenRepository refreshTokenRepository) {
        this.validityInDays = validityInDays;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken createToken(TokenPayload payload) {
        String value = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        RefreshToken refreshToken = new RefreshToken(value, now.plusDays(validityInDays), payload.id());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken reIssueToken(RefreshToken refreshToken) {
        RefreshToken newRefreshToken = createToken(new TokenPayload(refreshToken.getMemberId()));
        refreshTokenRepository.delete(refreshToken);
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken;
    }
}
