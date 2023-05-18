package com.kusitms.wannafly.Acceptance.fixture;

import com.kusitms.wannafly.command.category.dto.CategoryCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CategoryAcceptanceFixture {
    public static ExtractableResponse<Response> 카테고리를_생성한다(String accessToken, CategoryCreateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/categories")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 카테고리를_조회한다(String accessToken) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/categories")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 카테고리를_삭제한다(String accessToken, Long categoryId) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/api/categories/" + categoryId)
                .then().log().all()
                .extract();
    }
}

