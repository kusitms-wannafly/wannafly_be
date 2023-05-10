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

import static com.kusitms.wannafly.Acceptance.fixture.AcceptanceFixture.*;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_UPDATE_REQUEST;
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
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/application-forms/" + 1)
        );
    }

    @Test
    void 지원서를_수정한다() {
        // given
        String formId = 지원서를_등록한다(accessToken, FORM_CREATE_REQUEST)
                .header(HttpHeaders.LOCATION)
                .split("/")[2];

        // when
        ExtractableResponse<Response> response = 나의_지원서를_수정한다(
                accessToken, Long.parseLong(formId), FORM_UPDATE_REQUEST
        );

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 나의_지원서_하나를_조회한다() {
        // given
        String formId = 지원서를_등록한다(accessToken, FORM_CREATE_REQUEST)
                .header(HttpHeaders.LOCATION)
                .split("/")[2];

        // when
        ExtractableResponse<Response> response = 나의_지원서를_조회한다(accessToken, Long.parseLong(formId));

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
}