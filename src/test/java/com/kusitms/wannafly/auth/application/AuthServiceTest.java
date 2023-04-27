package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.dto.AuthorizationRequest;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.auth.token.TokenPayload;
import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.domain.MemberRepository;
import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ExtendWith(WannaflyTestIsolationExtension.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("로그인을 할 때")
    class LoginTest {

        private final LoginRequest loginRequest = new LoginRequest(
                "google", "이동규", "ldk@mail.com", "picture.com"
        );

        @DisplayName("처음인 사용자는 DB에 저장된다.")
        @Test
        void firstLogin() {
            // when
            LoginResponse loginResponse = authService.login(loginRequest);

            // then
            Long memberId = loginResponse.memberId();
            Optional<Member> joinedMember = memberRepository.findById(memberId);
            assertAll(
                    () -> assertThat(joinedMember).isPresent(),
                    () -> assertThat(loginResponse.accessToken()).isNotNull(),
                    () -> assertThat(memberRepository.findAll().size()).isOne()
            );
        }

        @DisplayName("가입된 사용자는 DB에 또 저장되지 않는다.")
        @Test
        void joinedLogin() {
            // given
            authService.login(loginRequest);

            // when
            LoginResponse loginResponse = authService.login(loginRequest);

            // then
            Long memberId = loginResponse.memberId();
            Optional<Member> joinedMember = memberRepository.findById(memberId);
            assertAll(
                    () -> assertThat(joinedMember).isPresent(),
                    () -> assertThat(loginResponse.accessToken()).isNotNull(),
                    () -> assertThat(memberRepository.findAll().size()).isOne()
            );
        }
    }

    @Nested
    @DisplayName("인가 요청을 받을 때")
    class AuthorizeTest {

        @DisplayName("유효한 토큰이면 인가한다.")
        @Test
        void valid() {
            // given
            Long id = 1L;
            String validToken = jwtTokenProvider.createToken(new TokenPayload(id));
            AuthorizationRequest request = new AuthorizationRequest(validToken);

            // when
            AuthorizationResponse actual = authService.authorize(request);

            // then
            assertAll(
                    () -> assertThat(actual.isAuthorized()).isTrue(),
                    () -> assertThat(actual.memberId()).isEqualTo(id)
            );
        }

        @DisplayName("유효하지 않은 토큰이면 인가하지 않는다.")
        @Test
        void inValid() {
            // given
            String inValidToken = "inValidToken.inValidToken.inValidToken";
            AuthorizationRequest request = new AuthorizationRequest(inValidToken);

            // when
            AuthorizationResponse actual = authService.authorize(request);

            // then
            assertAll(
                    () -> assertThat(actual.isAuthorized()).isFalse(),
                    () -> assertThat(actual.memberId()).isNull()
            );
        }
    }
}
