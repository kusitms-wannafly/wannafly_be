package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);

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
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);

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
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);

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
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);

        // when
        ExtractableResponse<Response> response = 지원서를_삭제한다(accessToken, formId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 나의_지원서를_작성_완료한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);

        // when
        ExtractableResponse<Response> response = 지원서_상태_변경(accessToken, formId);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getBoolean("isCompleted")).isEqualTo(true)
        );
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 완료_지원서를_작성중으로_한다() {
        // given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken);
        지원서_상태_변경(accessToken, formId);

        // when
        ExtractableResponse<Response> response = 지원서_상태_변경(accessToken, formId);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getBoolean("isCompleted")).isEqualTo(false)
        );
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private Long 지원서를_등록하고_ID를_응답(String accessToken) {
        return extractCreatedId(지원서를_등록한다(accessToken, FORM_CREATE_REQUEST));
    }

    private long extractCreatedId(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header(HttpHeaders.LOCATION).split("/")[2]);
    }
}
