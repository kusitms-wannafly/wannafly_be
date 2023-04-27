package com.kusitms.wannafly.Acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AcceptanceFixture {

    public static ExtractableResponse<Response> 소셜_로그인을_한다(String registrationId) {
        return RestAssured.given().log().all()
                .when()
                .get("/mock/oauth2/authorization/" + registrationId)
                .then().log().all()
                .extract();
    }
}
