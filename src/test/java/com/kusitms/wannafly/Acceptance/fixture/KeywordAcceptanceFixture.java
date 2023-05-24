package com.kusitms.wannafly.Acceptance.fixture;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;


public class KeywordAcceptanceFixture {
    public static ExtractableResponse<Response> 키워드로_지원항목을_조회한다(String accessToken,
                                                                String keyword){
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("keyword", keyword)
                .get("/api/application-items")
                .then().log().all()
                .extract();
    }
}
