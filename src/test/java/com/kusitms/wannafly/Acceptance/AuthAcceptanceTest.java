package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.auth.dto.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.kusitms.wannafly.Acceptance.fixture.AcceptanceFixture.소셜_로그인을_한다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인을_하면_회원_식별자와_엑세스_토큰_리프레시_토큰을_응답한다() {
        // given
        소셜_로그인을_한다("google");

        // when
        ExtractableResponse<Response> response =  소셜_로그인을_한다("google");

        // then
        LoginResponse actual = response.jsonPath().getObject(".", LoginResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),

                () -> assertThat(response.cookie("refreshToken")).isNotNull(),

                () -> assertThat(actual.memberId()).isNotNull(),
                () -> assertThat(actual.accessToken()).isNotNull()
        );
    }

    @Test
    void 다른_OAuth_Client_같은_이메일로_가입을_시도하면_400_예외가_발생한다() {
        // given
        소셜_로그인을_한다("google");

        // when
        ExtractableResponse<Response> response =  소셜_로그인을_한다("naver");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.jsonPath().getInt("errorCode")).isEqualTo(1001)
        );
    }

    @Test
    void 엑세스_토큰이_없으면_401_예외가_발생한다() {
        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .get("/api")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void 엑세스_토큰이_있으면_401_예외가_발생하지_않는다() {
        // given
        String accessToken = 소셜_로그인을_한다("google")
                .jsonPath()
                .getString("accessToken");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get("/api")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void 리프레시_토큰으로_엑세스_토큰을_재발급_한다() throws InterruptedException {
        // given
        ExtractableResponse<Response> beforeLogin = 소셜_로그인을_한다("google");
        String expiredAccessToken = beforeLogin.jsonPath().getString("accessToken");
        String beforeRefreshToken = beforeLogin.cookie("refreshToken");

        Thread.sleep(1000);


        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .cookie("refreshToken", beforeRefreshToken)
                .post("/accessToken")
                .then().log().all()
                .extract();

        // then
        String newRefreshToken = response.cookie("refreshToken");
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),

                () -> assertThat(newRefreshToken).isNotNull(),
                () -> assertThat(newRefreshToken).isNotEqualTo(beforeRefreshToken),
                () -> assertThat(response.jsonPath().getString("accessToken")).isNotEqualTo(expiredAccessToken)
        );
    }

    @Test
    void 리프레시_토큰이_유효하지_않으면_예외가_발생한다() {
        // given
        ExtractableResponse<Response> beforeLogin = 소셜_로그인을_한다("google");


        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when()
                .cookie("refreshToken", "fake-refresh-token")
                .post("/accessToken")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
