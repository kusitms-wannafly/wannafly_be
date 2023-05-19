package com.kusitms.wannafly.command.applicationform.application;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationFormRepository;
import com.kusitms.wannafly.command.applicationform.dto.FormStateRequest;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.query.service.ApplicationFormQueryService;
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

    private final LoginMember loginMember = new LoginMember(1L);

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
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when then
            LoginMember requester = new LoginMember(2L);
            assertThatThrownBy(() -> applicationFormService.updateForm(
                    formId, requester, FORM_UPDATE_REQUEST)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }

    @DisplayName("지원서를 삭제할 때")
    @Nested
    class DeleteTest {

        @Test
        void 로그인_회원이_지원서_작성자면_삭제_가능하다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            applicationFormService.deleteForm(formId, loginMember);

            // then
            Optional<ApplicationForm> actual = applicationFormRepository.findById(formId);
            assertThat(actual).isEmpty();
        }

        @Test
        void 로그인_회원이_지원서_작성자가_아니면_예외가_발생한다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when then
            LoginMember requester = new LoginMember(2L);
            assertThatThrownBy(() -> applicationFormService.deleteForm(formId, requester))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }

    @DisplayName("지원서의 작성 상태를 변경할 때")
    @Nested
    class ChangeTest {

        private final FormStateRequest completeRequest = new FormStateRequest(true);
        private final FormStateRequest notCompleteRequest = new FormStateRequest(false);

        @Test
        void 로그인_회원이_지원서_작성자면_변경_가능하다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            applicationFormService.changeState(formId, loginMember, completeRequest);

            // then
            ApplicationForm actual = applicationFormRepository.findById(formId).orElseThrow();
            assertThat(actual.isCompleted()).isEqualTo(true);
        }

        @Test
        void 완료_상태_지원서는_작성_중이_된다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);
            applicationFormService.changeState(formId, loginMember, completeRequest);

            // when
            applicationFormService.changeState(formId, loginMember, notCompleteRequest);

            // then
            ApplicationForm actual = applicationFormRepository.findById(formId).orElseThrow();
            assertThat(actual.isCompleted()).isEqualTo(false);
        }

        @Test
        void 로그인_회원이_지원서_작성자가_아니면_예외가_발생한다() {
            // given
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when then
            LoginMember requester = new LoginMember(2L);
            assertThatThrownBy(() -> applicationFormService.changeState(formId, requester, completeRequest))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
