package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.application.ApplicationFormService;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.applicationform.query.dto.PagingParams;
import com.kusitms.wannafly.applicationform.query.dto.SimpleFormResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
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

    @DisplayName("나의 지원서를 마지막 수정 시간 순으로 모두 조회할 때")
    @Nested
    @Sql(scripts = "/mock_forms_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    class FindByParamsTest {

        private Long cursor;
        private Integer size;
        private Integer year;
        private final LoginMember loginMember = new LoginMember(1L);

        @Test
        void 기본적으로_모든_년도에서_9개_조회한다() {
            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(9),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(18L, 17L, 16L, 15L, 14L, 13L, 12L, 11L, 10L)
            );
        }

        @Test
        void 커서_이후_9개를_조회한다() {
            // given
            cursor = 10L;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(9),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L)
            );
        }

        @Test
        void 특정_년도의_지원서만_조회한다() {
            // given
            year = 2022;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(6),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(12L, 11L, 10L, 9L, 8L, 7L)
            );
        }

        @Test
        void 다섯개만_조회한다() {
            // given
            size = 5;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(5),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(18L, 17L, 16L, 15L, 14L)
            );
        }

        @Test
        void 특정_년도의_커서_이후를_조회한다() {
            // given
            year = 2021;
            cursor = 4L;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(3L, 2L, 1L)
            );
        }

        @Test
        void 커서_이후_5개만_조회한다() {
            // given
            size = 5;
            cursor = 10L;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(5),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(9L, 8L, 7L, 6L, 5L)
            );
        }

        @Test
        void 특정_년도_5개만_조회한다() {
            // given
            size = 5;
            year = 2022;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(5),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(12L, 11L, 10L, 9L, 8L)
            );
        }

        @Test
        void 특정_년도_3개를_커서_이후로_조회한다() {
            // given
            size = 3;
            year = 2021;
            cursor = 5L;

            // when
            List<SimpleFormResponse> actual = applicationFormQueryService.findAllByCondition(
                    loginMember, new PagingParams(cursor, size, year)
            );

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(4L, 3L, 2L)
            );
        }
    }
}
