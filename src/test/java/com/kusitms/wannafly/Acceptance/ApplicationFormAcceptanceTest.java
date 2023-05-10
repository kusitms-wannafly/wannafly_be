package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.support.fixture.ApplicationFormText;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.kusitms.wannafly.Acceptance.fixture.AcceptanceFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormAcceptanceTest extends AcceptanceTest {

    private final ApplicationItemCreateRequest itemRequest = new ApplicationItemCreateRequest(
            ApplicationFormText.QUESTION,
            ApplicationFormText.ANSWER
    );

    private final ApplicationFormCreateRequest formRequest = new ApplicationFormCreateRequest(
            "큐시즘",
            2023,
            "first_half",
            List.of(itemRequest, itemRequest, itemRequest)
    );

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
        ExtractableResponse<Response> response = 지원서를_등록한다(accessToken, formRequest);


        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/application-forms/" + 1)
        );
    }

    @Test
    void 나의_지원서_하나를_조회한다() {
        // given
        String formId = 지원서를_등록한다(accessToken, formRequest)
                .header(HttpHeaders.LOCATION)
                .split("/")[2];

        // when
        ExtractableResponse<Response> response = 나의_지원서를_조회한다(accessToken, Long.parseLong(formId));

        // then
        ApplicationFormResponse actual = response.jsonPath().getObject(".", ApplicationFormResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(actual.recruiter()).isEqualTo(formRequest.recruiter()),
                () -> assertThat(actual.year()).isEqualTo(formRequest.year()),
                () -> assertThat(actual.semester()).isEqualTo(formRequest.semester()),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationQuestion)
                        .containsExactly(
                                ApplicationFormText.QUESTION,
                                ApplicationFormText.QUESTION,
                                ApplicationFormText.QUESTION
                        ),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationAnswer)
                        .containsExactly(
                                ApplicationFormText.ANSWER,
                                ApplicationFormText.ANSWER,
                                ApplicationFormText.ANSWER
                        )
        );
    }
}
