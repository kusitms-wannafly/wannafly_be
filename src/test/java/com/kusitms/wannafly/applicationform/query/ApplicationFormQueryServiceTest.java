package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.application.ApplicationFormService;
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

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormQueryServiceTest extends ServiceTest {

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private ApplicationFormQueryService applicationFormQueryService;


    @DisplayName("나의 지원서를 조회할 때")
    @Nested
    class FindOneTest {

        @Test
        void 로그인_회원이_지원서를_작성했다면_조회한다() {
            // given
            LoginMember loginMember = new LoginMember(1L);
            Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

            // when
            ApplicationFormResponse response = applicationFormQueryService.findOne(formId, loginMember);

            // then
            assertAll(
                    () -> assertThat(response.recruiter()).isEqualTo(FORM_CREATE_REQUEST.recruiter()),
                    () -> assertThat(response.year()).isEqualTo(FORM_CREATE_REQUEST.year()),
                    () -> assertThat(response.semester()).isEqualTo(FORM_CREATE_REQUEST.semester()),
                    () -> assertThat(response.applicationItems())
                            .map(ApplicationItemResponse::applicationQuestion)
                            .containsExactly(
                                    ApplicationFormFixture.QUESTION1,
                                    ApplicationFormFixture.QUESTION1,
                                    ApplicationFormFixture.QUESTION1
                            ),
                    () -> assertThat(response.applicationItems())
                            .map(ApplicationItemResponse::applicationAnswer)
                            .containsExactly(
                                    ApplicationFormFixture.ANSWER1,
                                    ApplicationFormFixture.ANSWER1,
                                    ApplicationFormFixture.ANSWER1
                            )
            );
        }

        @Test
        void 지원서가_존재하지_않으면_예외가_발생한다() {
            // given
            LoginMember loginMember = new LoginMember(1L);

            // when then
            assertThatThrownBy(() -> applicationFormQueryService.findOne(1L, loginMember))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.NOT_FOUND_APPLICATION_FORM);
        }

        @Test
        void 로그인_회원이_지원서를_작성하지_않았으면_예외가_발생한다() {
            // given
            LoginMember writerOfForm = new LoginMember(1L);
            Long formId = applicationFormService.createForm(writerOfForm, FORM_CREATE_REQUEST);

            // when then
            assertThatThrownBy(() -> applicationFormQueryService.findOne(formId, new LoginMember(2L)))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }

    }

}
