package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.query.ApplicationFormQueryService;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormServiceTest extends ServiceTest {

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private ApplicationFormQueryService applicationFormQueryService;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Test
    void 지원서를_등록한다() {
        // when
        Long formId = applicationFormService.createForm(new LoginMember(1L), FORM_CREATE_REQUEST);

        // then
        Optional<ApplicationForm> savedForm = applicationFormRepository.findById(formId);
        assertAll(
                () -> assertThat(formId).isEqualTo(formId),
                () -> assertThat(savedForm).isPresent()
        );
    }

    @DisplayName("지원서를 수정할 때")
    @Nested
    class UpdateTest {

        @Test
        void 로그인_회원이_지원서_작성자면_수정_가능하다() {
            // given
            LoginMember loginMember = new LoginMember(1L);
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            applicationFormService.updateForm(
                    formId, loginMember, FORM_UPDATE_REQUEST
            );

            // then
            ApplicationFormResponse actual = applicationFormQueryService.findOne(formId, loginMember);
            assertAll(
                    () -> assertThat(actual.recruiter()).isEqualTo(FORM_UPDATE_REQUEST.recruiter()),
                    () -> assertThat(actual.year()).isEqualTo(FORM_UPDATE_REQUEST.year()),
                    () -> assertThat(actual.semester()).isEqualTo(FORM_UPDATE_REQUEST.semester()),
                    () -> assertThat(actual.applicationItems())
                            .map(ApplicationItemResponse::applicationQuestion)
                            .containsExactly(
                                    ApplicationFormFixture.QUESTION2,
                                    ApplicationFormFixture.QUESTION1,
                                    ApplicationFormFixture.QUESTION2
                            ),
                    () -> assertThat(actual.applicationItems())
                            .map(ApplicationItemResponse::applicationAnswer)
                            .containsExactly(
                                    ApplicationFormFixture.ANSWER2,
                                    ApplicationFormFixture.ANSWER1,
                                    ApplicationFormFixture.ANSWER2
                            )
            );
        }

        @Test
        void 로그인_회원이_지원서_작성자가_아니면_예외가_발생한다() {
            // given
            LoginMember formWriter = new LoginMember(1L);
            Long formId = applicationFormService.createForm(formWriter, FORM_CREATE_REQUEST);

            // when then
            LoginMember loginMember = new LoginMember(2L);
            assertThatThrownBy(() -> applicationFormService.updateForm(
                    formId, loginMember, FORM_UPDATE_REQUEST)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }

    @DisplayName("지원서의 지원 항목을 추가할 때")
    @Nested
    class AddItemTest {

        @Test
        void 로그인_회원이_지원서_작성자면_추가_가능하다() {
            // given
            LoginMember loginMember = new LoginMember(1L);
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            Long itemId = applicationFormService.addItem(formId, loginMember, ITEM_CREATE_REQUEST);

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
            LoginMember formWriter = new LoginMember(1L);
            Long formId = applicationFormService.createForm(formWriter, FORM_CREATE_REQUEST);

            // when then
            LoginMember loginMember = new LoginMember(2L);
            assertThatThrownBy(() -> applicationFormService.addItem(
                    formId, loginMember, ITEM_CREATE_REQUEST)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
