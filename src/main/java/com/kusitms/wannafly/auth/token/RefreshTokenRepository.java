package com.kusitms.wannafly.auth.token;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByValue(String refreshTokenValue);

    void delete(RefreshToken refreshToken);
}
