package com.kusitms.wannafly.auth.token;

import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RefreshTokenProviderTest extends ServiceTest {

    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void 리프레시_토큰을_발급한다() {
        // given
        TokenPayload payload = new TokenPayload(1L);

        // when
        RefreshToken refreshToken = refreshTokenProvider.createToken(payload);

        // then
        LocalDateTime now = LocalDateTime.now();
        Optional<RefreshToken> savedToken = refreshTokenRepository.findByValue(refreshToken.getValue());
        assertAll(
                () -> assertThat(refreshToken.getValue()).isNotNull(),
                () -> assertThat(refreshToken.isValid(now)).isTrue(),
                () -> assertThat(savedToken).isPresent()
        );
    }

    @Test
    void 리프레시_토큰을_재발급한다() {
        // given
        TokenPayload payload = new TokenPayload(1L);
        RefreshToken previousRefreshToken = refreshTokenProvider.createToken(payload);

        // when
        RefreshToken newRefreshToken = refreshTokenProvider.reIssueToken(previousRefreshToken);

        // then
        Optional<RefreshToken> previousSaved = refreshTokenRepository.findByValue(previousRefreshToken.getValue());
        Optional<RefreshToken> newSaved = refreshTokenRepository.findByValue(newRefreshToken.getValue());
        assertAll(
                () -> assertThat(newRefreshToken.getValue()).isNotEqualTo(previousRefreshToken.getValue()),
                () -> assertThat(previousSaved).isEmpty(),
                () -> assertThat(newSaved).isPresent()
        );
    }
}
