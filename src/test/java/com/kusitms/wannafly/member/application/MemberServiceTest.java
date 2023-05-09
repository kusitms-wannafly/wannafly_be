package com.kusitms.wannafly.member.application;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.member.domain.Member;
import com.kusitms.wannafly.member.dto.MemberRequest;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    private final MemberRequest request = new MemberRequest(
            "google", "이동규", "ldk@gmail.com", "picture.com"
    );


    @Nested
    @DisplayName("사용자가 서비스에 가입할 때")
    class JoinTest {

        @Test
        void 사용자가_저장_된다() {
            // when
            Member member = memberService.join(request);

            // then
            assertThat(member.getId()).isNotNull();
        }

        @Test
        void 이미_가입된_email이면_예외가_발생한다() {
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
