package com.kusitms.wannafly.Acceptance.fixture;

import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

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
    public static ExtractableResponse<Response> 지원서_보관함을_생성한다(String accessToken, ApplicationFolderCreateRequest request){
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/application-folders")
                .then().log().all()
                .extract();
    }
    public static ExtractableResponse<Response> 지원서를_조회한다(String accessToken){
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/application-folders")
                .then().log().all()
                .extract();
    }
}

