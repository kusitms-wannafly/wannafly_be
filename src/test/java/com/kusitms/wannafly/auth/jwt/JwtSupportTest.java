package com.kusitms.wannafly.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

class JwtSupportTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @DisplayName("HTTP Authorization 헤더에서 토큰을 추출한다.")
    @Test
    void extract() {
        // given
        String expected = "mockToken.mockToken.mockToken";
        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn("Bearer " + expected);

        // when
        Optional<String> actual = JwtSupport.extractToken(request);

        // then
        assertThat(actual).get().isEqualTo(expected);
    }
}
