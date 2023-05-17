package com.kusitms.wannafly.command.auth.application;

import com.kusitms.wannafly.command.auth.dto.*;
import com.kusitms.wannafly.command.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.command.auth.token.RefreshToken;
import com.kusitms.wannafly.command.auth.token.RefreshTokenRepository;
import com.kusitms.wannafly.command.auth.token.TokenPayload;
import com.kusitms.wannafly.command.member.domain.Member;
import com.kusitms.wannafly.command.member.domain.MemberRepository;
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

class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final LoginRequest loginRequest = new LoginRequest(
            "google", "이동규", "ldk@mail.com", "picture.com"
    );

    @Nested
    @DisplayName("로그인을 할 때")
    class LoginTest {

        @Test
        void 처음인_사용자는_DB에_저장된다() {
            // when˚
            LoginResponse loginResponse = authService.login(loginRequest);

            // then
            Long memberId = loginResponse.memberId();
            Optional<Member> joinedMember = memberRepository.findById(memberId);
            assertAll(
                    () -> assertThat(joinedMember).isPresent(),
                    () -> assertThat(loginResponse.accessToken()).isNotNull(),
                    () -> assertThat(loginResponse.refreshToken()).isNotNull(),
                    () -> assertThat(memberRepository.findAll().size()).isOne()
            );
        }

        @Test
        void 리프레시_토큰이_저장된다() {
            // when
            LoginResponse loginResponse = authService.login(loginRequest);

            // then
            Long memberId = loginResponse.memberId();
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByValue(loginResponse.refreshToken());
            assertThat(refreshToken)
                    .map(RefreshToken::getMemberId)
                    .get()
                    .isEqualTo(memberId);
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

    @Nested
    @DisplayName("엑세스 토큰을 재발급 할 때")
    class ReIssueTest {

        @Test
        void 유효한_리프레시_토큰이면_재발급_한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().plusDays(1);
            String refreshTokenValue = "valid-refresh-token";
            RefreshToken refreshToken = new RefreshToken(refreshTokenValue, expiredTime, 1L);
            refreshTokenRepository.save(refreshToken);

            // when
            ReIssueResponse actual = authService.reIssueTokens(refreshTokenValue);

            // then
            assertAll(
                    () -> assertThat(actual.memberId()).isEqualTo(1L),
                    () -> assertThat(actual.accessToken()).isNotNull()
            );
        }

        @Test
        void 저장소의_리프레시_토큰을_교체한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().plusDays(1);
            String previousRefreshToken = "previousRefreshToken";
            RefreshToken refreshToken = new RefreshToken(previousRefreshToken, expiredTime, 1L);
            refreshTokenRepository.save(refreshToken);

            // when
            ReIssueResponse actual = authService.reIssueTokens(previousRefreshToken);

            // then
            Optional<RefreshToken> previous = refreshTokenRepository.findByValue(previousRefreshToken);
            Optional<RefreshToken> reissued = refreshTokenRepository.findByValue(actual.refreshToken());
            assertAll(
                    () -> assertThat(previous).isEmpty(),
                    () -> assertThat(reissued)
                            .map(RefreshToken::getValue)
                            .get()
                            .isNotEqualTo(previousRefreshToken)
            );
        }

        @Test
        void 저장소에_없는_토큰이면_예외가_발생한다() {
            // given
            String refreshTokenValue = "invalid-refresh-token";

            // when // then
            assertThatThrownBy(() -> authService.reIssueTokens(refreshTokenValue))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY);
        }

        @Test
        void 만료된_리프레시_토큰이면_예외가_발생한다() {
            // given
            LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(1);
            String refreshTokenValue = "invalid-refresh-token";
            RefreshToken refreshToken = new RefreshToken(refreshTokenValue, expiredTime, 1L);
            refreshTokenRepository.save(refreshToken);

            // when // then
            assertThatThrownBy(() -> authService.reIssueTokens(refreshTokenValue))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    @Nested
    @DisplayName("로그아웃을 할 때")
    class LogoutTest {

        @Test
        void 리프레시_토큰을_삭제한다() {
            // given
            String refreshTokenValue = authService.login(loginRequest)
                    .refreshToken();

            // when
            authService.logoutRefreshToken(refreshTokenValue);

            // then
            Optional<RefreshToken> actual = refreshTokenRepository.findByValue(refreshTokenValue);
            assertThat(actual).isEmpty();
        }
    }
}
