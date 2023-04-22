package com.kusitms.wannafly.auth.application;

import com.kusitms.wannafly.auth.dto.LoginRequest;
import com.kusitms.wannafly.auth.dto.LoginResponse;
import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.domain.MemberRepository;
import com.kusitms.wannafly.support.WannaflyTestIsolationExtension;
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

    @Nested
    @DisplayName("로그인을 할 때")
    class LoginTest {

        private final LoginRequest loginRequest = new LoginRequest(
                "이동규", "ldk@mail.com", "picture.com"
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
}
