package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.support.fixture.ApplicationFormText;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.kusitms.wannafly.Acceptance.fixture.AcceptanceFixture.소셜_로그인을_한다;
import static com.kusitms.wannafly.Acceptance.fixture.AcceptanceFixture.지원서를_등록한다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormAcceptanceTest extends AcceptanceTest {


    @Test
    void 지원서를_작성하여_등록한다() {
        // given
        String accessToken = 소셜_로그인을_한다("google")
                .jsonPath()
                .getString("accessToken");
        ApplicationItemCreateRequest itemRequest = new ApplicationItemCreateRequest(
                ApplicationFormText.QUESTION,
                ApplicationFormText.ANSWER
        );
        ApplicationFormCreateRequest formRequest = new ApplicationFormCreateRequest(
                "큐시즘",
                2023,
                "first_half",
                List.of(itemRequest, itemRequest, itemRequest)
        );

        // when
        ExtractableResponse<Response> response = 지원서를_등록한다(accessToken, formRequest);


        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/application-forms/" + 1)
        );
    }
}
