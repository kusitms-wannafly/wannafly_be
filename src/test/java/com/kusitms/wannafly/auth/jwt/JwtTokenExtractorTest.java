package com.kusitms.wannafly.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

class JwtTokenExtractorTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @DisplayName("HTTP Authorization 헤더에서 토큰을 추출한다.")
    @Test
    void extract() {
        // given
        String expected = "mockToken.mockToken.mockToken";
        given(request.getHeader(HttpHeaders.AUTHORIZATION))
                .willReturn("Bearer " + expected);

        // when
        String actual = JwtTokenExtractor.extract(request);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
