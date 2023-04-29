package com.kusitms.wannafly.auth.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RefreshTokenProvider {

    private static final String EXPIRE_LENGTH = "${security.refresh.token.expire-day}";

    private final long validityInDays;

    public RefreshTokenProvider(@Value(EXPIRE_LENGTH) long validityInDays) {
        this.validityInDays = validityInDays;
    }

    public RefreshToken createToken(TokenPayload payload) {
        String value = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        return new RefreshToken(value, now.plusDays(validityInDays), payload.id());
    }
}
