package com.kusitms.wannafly.auth.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final TokenPayload payload = new TokenPayload(1L);

    @DisplayName("JWT 토큰을 생성한다.")
    @Test
    void createToken() {
        // when
        String actual = tokenProvider.createToken(payload);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("JWT 토큰에서 payload를 추출한다.")
    @Test
    void getPayload() {
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

        @DisplayName("유효한 경우 True를 반환한다.")
        @Test
        void isValid() {
            // given
            String token = tokenProvider.createToken(payload);

            // when
            boolean actual = tokenProvider.isValidAccessToken(token);

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("유효하지 않은 경우 False를 반환한다.")
        @Test
        void isNotValid() {
            // given
            String token = "notValidToken.notValidToken.notValidToken";

            // when
            boolean actual = tokenProvider.isValidAccessToken(token);

            // then
            assertThat(actual).isFalse();
        }
    }
}
