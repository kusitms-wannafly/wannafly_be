package com.kusitms.wannafly.command.auth.token;

import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenRepositoryTest extends ServiceTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final LocalDateTime now = LocalDateTime.now();

    @DisplayName("리프레시 토큰을 조회할 때")
    @Nested
    class FindTokenTest {
        @Test
        void 만료_시간_전에는_성공한다() {
            // given
            RefreshToken token = new RefreshToken("value", now.plusSeconds(5L), 1L);
            refreshTokenRepository.save(token);

            // when
            Optional<RefreshToken> actual = refreshTokenRepository.findByValue(token.getValue());

            // then
            assertThat(actual).isPresent();
        }

        @Test
        void 만료_시간이_지나면_조회되지_않는다() throws InterruptedException {
            // given
            RefreshToken token = new RefreshToken("value", now.plusSeconds(1L), 1L);
            refreshTokenRepository.save(token);

            // when
            Thread.sleep(2000);
            Optional<RefreshToken> actual = refreshTokenRepository.findByValue(token.getValue());

            // then
            assertThat(actual).isEmpty();
        }
    }
}
