package com.kusitms.wannafly.command.auth.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @Nested
    @DisplayName("리프레시 토큰이")
    class IsExpiredTest {

        @Test
        void 유효하면_True를_반환한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(1L);
            RefreshToken refreshToken = new RefreshToken("value", expiredTime, 1L);

            // when
            boolean actual = refreshToken.isValid(LocalDateTime.now());

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 유효하지_않으면_False를_반환한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(1L);
            RefreshToken refreshToken = new RefreshToken("value", expiredTime, 1L);

            // when
            boolean actual = refreshToken.isValid(LocalDateTime.now());

            // then
            assertThat(actual).isFalse();
        }
    }
}
