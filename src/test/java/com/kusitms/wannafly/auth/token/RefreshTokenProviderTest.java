package com.kusitms.wannafly.auth.token;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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


    @Nested
    @DisplayName("리프레시 토큰을 재발급할 때")
    class ReIssueTest {

        @Test
        void 저장소_리프레시_토큰이_교체된다() {
            // given
            TokenPayload payload = new TokenPayload(1L);
            RefreshToken previousRefreshToken = refreshTokenProvider.createToken(payload);

            // when
            RefreshToken newRefreshToken = refreshTokenProvider.reIssueToken(previousRefreshToken.getValue());

            // then
            Optional<RefreshToken> previousSaved = refreshTokenRepository.findByValue(previousRefreshToken.getValue());
            Optional<RefreshToken> newSaved = refreshTokenRepository.findByValue(newRefreshToken.getValue());
            assertAll(
                    () -> assertThat(newRefreshToken.getValue()).isNotEqualTo(previousRefreshToken.getValue()),
                    () -> assertThat(previousSaved).isEmpty(),
                    () -> assertThat(newSaved).isPresent()
            );
        }

        @Test
        void 리프레시_토큰이_유효하지_않으면_예외가_발생한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().minusDays(1);
            String refreshTokenValue = "value";
            RefreshToken previousRefreshToken = new RefreshToken(refreshTokenValue, expiredTime, 1L);
            refreshTokenRepository.save(previousRefreshToken);

            // when then
            assertThatThrownBy(() -> refreshTokenProvider.reIssueToken(refreshTokenValue))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

    }
}
