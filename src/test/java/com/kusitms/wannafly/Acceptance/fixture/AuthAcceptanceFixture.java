package com.kusitms.wannafly.Acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthAcceptanceFixture {

    public static ExtractableResponse<Response> 소셜_로그인을_한다(String registrationId) {
        return RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/" + registrationId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰을_재발급_한다(String refreshToken) {
        return RestAssured.given().log().all()
                .when()
                .cookie("refreshToken", refreshToken)
                .post("/accessToken")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그아웃_한다(String refreshToken) {
        return RestAssured.given().log().all()
                .when()
                .cookie("refreshToken", refreshToken)
                .delete("/refreshToken")
                .then().log().all()
                .extract();
    }
}
