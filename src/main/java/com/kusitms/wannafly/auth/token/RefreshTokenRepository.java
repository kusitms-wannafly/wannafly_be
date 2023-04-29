package com.kusitms.wannafly.auth.token;

public interface RefreshTokenRepository {

    String save(RefreshToken refreshToken);
}
