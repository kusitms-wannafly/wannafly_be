package com.kusitms.wannafly.command.applicationform.application;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.query.service.ApplicationFormQueryService;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.ITEM_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationItemServiceTest extends ServiceTest {

    @Autowired
    private ApplicationItemService applicationItemService;

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private ApplicationFormQueryService applicationFormQueryService;

    private final LoginMember loginMember = new LoginMember(1L);

    @DisplayName("지원서의 지원 항목을 추가할 때")
    @Nested
    class AddItemTest {

        @Test
        void 로그인_회원이_지원서_작성자면_추가_가능하다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            Long itemId = applicationItemService.addItem(formId, loginMember, ITEM_CREATE_REQUEST);

            // then
            ApplicationFormResponse form = applicationFormQueryService.findOne(formId, loginMember);
            assertAll(
                    () -> assertThat(itemId).isEqualTo(4),
                    () -> assertThat(form.applicationItems()).hasSize(4)
            );
        }

        @Test
        void 로그인_회원이_지원서_작성자가_아니면_예외가_발생한다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when then
            LoginMember requester = new LoginMember(2L);
            assertThatThrownBy(() -> applicationItemService.addItem(
                    formId, requester, ITEM_CREATE_REQUEST)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }

}
