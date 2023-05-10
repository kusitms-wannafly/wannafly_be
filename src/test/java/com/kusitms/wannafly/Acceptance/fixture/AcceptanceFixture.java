package com.kusitms.wannafly.Acceptance.fixture;

import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AcceptanceFixture {

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

    public static ExtractableResponse<Response> 지원서를_등록한다(String accessToken, ApplicationFormCreateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/application-forms")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_지원서를_조회한다(String accessToken, Long formId) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/application-forms/" + formId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 나의_지원서를_수정한다(String accessToken,
                                                             Long formId,
                                                             ApplicationFormUpdateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .patch("/api/application-forms/" + formId)
                .then().log().all()
                .extract();
    }
}
