package com.kusitms.wannafly.command.auth.token;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

class JwtSupportTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void HTTP_Authorization_헤더에서_토큰을_추출한다() {
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
