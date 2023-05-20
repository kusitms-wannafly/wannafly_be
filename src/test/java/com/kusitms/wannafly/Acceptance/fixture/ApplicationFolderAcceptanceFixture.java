package com.kusitms.wannafly.Acceptance.fixture;

import com.kusitms.wannafly.command.applicationfolder.dto.ApplicationFolderCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ApplicationFolderAcceptanceFixture {
    public static ExtractableResponse<Response> 지원서_보관함을_생성한다(String accessToken, ApplicationFolderCreateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/application-folders")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지원서_보관함을_조회한다(String accessToken) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/application-folders")
                .then().log().all()
                .extract();
    }
}
