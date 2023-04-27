package com.kusitms.wannafly.member.application;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.dto.MemberRequest;
import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(WannaflyTestIsolationExtension.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private final MemberRequest request = new MemberRequest(
            "google", "이동규", "ldk@gmail.com", "picture.com"
    );


    @Nested
    @DisplayName("사용자가 서비스에 가입할 때")
    class JoinTest {

        @DisplayName("Member가 저장된다.")
        @Test
        void join() {
            // when
            Member member = memberService.join(request);

            // then
            assertThat(member.getId()).isNotNull();
        }

        @DisplayName("이미 가입된 email이면 예외가 발생한다.")
        @Test
        void duplicatedEmail() {
            // given
            memberService.join(request);

            // when then
            assertThatThrownBy(() -> memberService.join(request))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MEMBER_DUPLICATE_EMAIL);
        }
    }
}
