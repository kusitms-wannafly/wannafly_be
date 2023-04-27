package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.auth.dto.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인을 하면 회원 식별자와 엑세스 토큰, 리프레시 토큰을 응답한다.")
    @Test
    void oauthLogin() {
        // given
        RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/google")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/google")
                .then().log().all()
                .extract();

        // then
        LoginResponse actual = response.jsonPath().getObject(".", LoginResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),

                () -> assertThat(actual.memberId()).isNotNull(),
                () -> assertThat(actual.accessToken()).isNotNull()
        );
    }

    @DisplayName("다른 OAuth Client, 같은 이메일로 가입을 시도하면 400 예외가 발생한다.")
    @Test
    void oauthLoginDuplicatedEmail() {
        // given
        RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/google")
                .then().log().all()
                .extract();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/naver")
                .then().log().all()
                .extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(1001)
        );
    }
}
