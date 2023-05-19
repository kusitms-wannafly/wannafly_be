package com.kusitms.wannafly.Acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.kusitms.wannafly.Acceptance.fixture.AuthAcceptanceFixture.*;
import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2023;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ApplicationFolderAcceptanceTest extends AcceptanceTest {
    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = 소셜_로그인을_한다("google")
                .jsonPath()
                .getString("accessToken");
    }

    @Test
    void 원하는_년도의_지원서_보관함을_생성한다() {
        //when
        ExtractableResponse<Response> response = 지원서_보관함을_생성한다(accessToken, FOLDER_CREATE_2023);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/application-folders/" + 1)
        );
    }

    @Test
    void 나의_지원서_보관함을_조회한다() {
        //given
        지원서_보관함을_생성한다(accessToken, FOLDER_CREATE_2023);

        //when
        ExtractableResponse<Response> response = 지원서를_조회한다(accessToken);

        //then
        int year = response.body().jsonPath().getInt("[0].year");
        int count = response.body().jsonPath().getInt("[0].count");
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(year).isEqualTo(2023),
                () -> assertThat(count).isEqualTo(0)
        );
    }
}
