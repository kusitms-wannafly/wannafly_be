package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.dto.AuthorizationRequest;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.auth.token.TokenPayload;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.domain.MemberRepository;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthServiceTest extends ServiceTest {

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

        @Test
        void 처음인_사용자는_DB에_저장된다() {
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

        @DisplayName("같은 OAuth Client로 가입된 사용자는 DB에 또 저장되지 않는다.")
        @Test
        void 같은_OAuth_Client로_가입된_사용자는_DB에_또_저장되지_않는다() {
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

        @Test
        void 다른_OAuth_Client이지만_email이_같으면_예외가_발생한다() {
            // given
            authService.login(loginRequest);
            LoginRequest duplicatedEmailRequest = new LoginRequest(
                    "naver", "이동규", "ldk@mail.com", "picture.com"
            );

            // when then
            assertThatThrownBy(() -> authService.login(duplicatedEmailRequest))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
    }

    @Nested
    @DisplayName("인가 요청을 받을 때")
    class AuthorizeTest {

        @Test
        void 유효한_토큰이면_인가한다() {
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

        @Test
        void 유효하지_않은_토큰이면_인가하지_않는다() {
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
