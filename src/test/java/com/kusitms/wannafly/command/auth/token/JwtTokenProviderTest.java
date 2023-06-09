package com.kusitms.wannafly.command.auth.token;

import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest extends ServiceTest {

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final TokenPayload payload = new TokenPayload(1L);

    @Test
    void JWT_토큰을_생성한다() {
        // when
        String actual = tokenProvider.createToken(payload);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    void JWT_토큰에서_payload를_추출한다() {
        // given
        String token = tokenProvider.createToken(payload);

        // when
        TokenPayload actual = tokenProvider.getPayload(token);

        // then
        assertThat(actual).isEqualTo(payload);
    }

    @Nested
    @DisplayName("JWT 토큰이")
    class ValidAccessTokenTest {

        @Test
        void 유효한_경우_True를_반환한다() {
            // given
            String token = tokenProvider.createToken(payload);

            // when
            boolean actual = tokenProvider.isValidAccessToken(token);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 유효하지_않은_경우_False를_반환한다() {
            // given
            String token = "notValidToken.notValidToken.notValidToken";

            // when
            boolean actual = tokenProvider.isValidAccessToken(token);

            // then
            assertThat(actual).isFalse();
        }
    }
}
