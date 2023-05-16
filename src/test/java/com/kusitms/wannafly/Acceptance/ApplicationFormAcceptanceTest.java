package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.FormStateRequest;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.applicationform.query.dto.SimpleFormResponse;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.kusitms.wannafly.Acceptance.fixture.ApplicationFormAcceptanceFixture.*;
import static com.kusitms.wannafly.Acceptance.fixture.AuthAcceptanceFixture.소셜_로그인을_한다;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormAcceptanceTest extends AcceptanceTest {

    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = 소셜_로그인을_한다("google")
                .jsonPath()
                .getString("accessToken");
    }


    @Test
    void 지원서를_작성하여_등록한다() {
        // when
        ExtractableResponse<Response> response = 지원서를_등록한다(accessToken, FORM_CREATE_REQUEST);


        // then
        long applicationFormId = extractCreatedId(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(applicationFormId).isEqualTo(1)
        );
    }

    @Test
    void 지원서를_수정한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 나의_지원서를_수정한다(
                accessToken, formId, FORM_UPDATE_REQUEST
        );

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 나의_지원서_하나를_조회한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 나의_지원서를_조회한다(accessToken, formId);

        // then
        ApplicationFormResponse actual = response.jsonPath().getObject(".", ApplicationFormResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(actual.recruiter()).isEqualTo(FORM_CREATE_REQUEST.recruiter()),
                () -> assertThat(actual.year()).isEqualTo(FORM_CREATE_REQUEST.year()),
                () -> assertThat(actual.semester()).isEqualTo(FORM_CREATE_REQUEST.semester()),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationQuestion)
                        .containsExactly(
                                ApplicationFormFixture.QUESTION1,
                                ApplicationFormFixture.QUESTION1,
                                ApplicationFormFixture.QUESTION1
                        ),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationAnswer)
                        .containsExactly(
                                ApplicationFormFixture.ANSWER1,
                                ApplicationFormFixture.ANSWER1,
                                ApplicationFormFixture.ANSWER1
                        )
        );
    }

    @Test
    void 지원서의_지원_항목을_추가한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 지원_항목을_추가한다(accessToken, formId, ITEM_CREATE_REQUEST);

        // then
        long applicationItemId = extractCreatedId(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(applicationItemId).isEqualTo(4)
        );
    }

    @Test
    void 나의_지원서를_삭제한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 지원서를_삭제한다(accessToken, formId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 나의_지원서를_작성_완료한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);
        FormStateRequest request = new FormStateRequest(true);

        // when
        ExtractableResponse<Response> response = 지원서_상태_변경(accessToken, formId, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 완료_지원서를_작성중으로_한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST);
        FormStateRequest request = new FormStateRequest(true);

        // when
        ExtractableResponse<Response> response = 지원서_상태_변경(accessToken, formId, request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("나의 지원서들을 마지막 수정시간 순으로 조회할 때")
    @Nested
    class GetFormsTest {

        private Long formId1;
        private Long formId2;
        private Long formId3;
        private Long formId4;
        private Long formId5;
        private Long formId6;
        private Long formId7;
        private Long formId8;
        private Long formId9;
        private Long formId10;

        @BeforeEach
        void createForm() {
            formId1 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2019_1);
            formId2 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2019_2);
            formId3 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2020_1);
            formId4 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2020_2);
            formId5 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2021_1);
            formId6 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2021_2);
            formId7 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2022_1);
            formId8 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2022_2);
            formId9 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2023_1);
            formId10 = 지원서를_등록하고_ID를_응답(accessToken, FORM_2023_2);
        }

        @Test
        void 모든_년도에서_9개를_조회한다() {
            // when
            ExtractableResponse<Response> response = 지원서들을_조회한다(accessToken, null, null, null);

            // then
            List<SimpleFormResponse> actual = response.jsonPath().getList(".", SimpleFormResponse.class);
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual).hasSize(9),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(
                                    formId10, formId9, formId8, formId7, formId6, formId5, formId4, formId3, formId2
                            )
            );
        }

        @Test
        void 모든_년도에서_1개를_조회한다() {
            // when
            ExtractableResponse<Response> response = 지원서들을_조회한다(accessToken, null, 1, null);

            // then
            List<SimpleFormResponse> actual = response.jsonPath().getList(".", SimpleFormResponse.class);
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual).hasSize(1),
                    () -> assertThat(actual)
                            .extracting("year")
                            .containsExactly(2023),
                    () -> assertThat(actual)
                            .extracting("semester")
                            .containsExactly("second_half")
            );
        }

        @Test
        void 특정_년도에서_2개를_조회한다() {
            // when
            ExtractableResponse<Response> response = 지원서들을_조회한다(accessToken, null, 2, 2021);

            // then
            List<SimpleFormResponse> actual = response.jsonPath().getList(".", SimpleFormResponse.class);
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual).hasSize(2),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(formId6, formId5)
            );
        }

        @Test
        void 모든_년도에서_5개_조회_후_다음_5개를_마저_조회한다() {
            // given
            Integer size = 5;
            Long cursor = 지원서들을_조회한다(accessToken, null, size, null)
                    .jsonPath()
                    .getList(".", SimpleFormResponse.class)
                    .get(4)
                    .applicationFormId();

            // when
            ExtractableResponse<Response> response = 지원서들을_조회한다(accessToken, cursor, size, null);

            // then
            List<SimpleFormResponse> actual = response.jsonPath().getList(".", SimpleFormResponse.class);
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(actual).hasSize(size),
                    () -> assertThat(actual)
                            .extracting("applicationFormId")
                            .containsExactly(formId5, formId4, formId3, formId2, formId1)
            );
        }
    }

    private Long 지원서를_등록하고_ID를_응답(String accessToken, ApplicationFormCreateRequest request) {
        return extractCreatedId(지원서를_등록한다(accessToken, request));
    }

    private long extractCreatedId(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header(HttpHeaders.LOCATION).split("/")[2]);
    }
}
