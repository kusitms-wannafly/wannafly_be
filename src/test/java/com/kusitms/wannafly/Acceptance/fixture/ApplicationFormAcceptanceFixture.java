package com.kusitms.wannafly.Acceptance.fixture;

import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.FormStateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class ApplicationFormAcceptanceFixture {

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

    public static ExtractableResponse<Response> 지원_항목을_추가한다(String accessToken,
                                                            Long formId,
                                                            ApplicationItemCreateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .post("/api/application-forms/" + formId + "/items")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지원서를_삭제한다(String accessToken, Long formId) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/api/application-forms/" + formId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지원서_상태_변경(String accessToken, Long formId, FormStateRequest request) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .patch("/api/application-forms/" + formId + "/state")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지원서들을_조회한다(String accessToken,
                                                           Long cursor,
                                                           Integer size,
                                                           Integer year) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParams(getQueryString(cursor, size, year))
                .get("/api/application-forms")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지원_항목에_카테고리를_등록한다(String accessToken, Long categoryId, Long itemId) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post("/api/categories/" + categoryId + "/application-items/" + itemId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 카테고리로_지원_항목을_조회한다(String accessToken, Long categoryId) {
        return RestAssured.given().log().all()
                .when()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/api/categories/" + categoryId + "/application-items")
                .then().log().all()
                .extract();
    }



    private static Map<String, Object> getQueryString(Long cursor, Integer size, Integer year) {
        Map<String, Object> params = new HashMap<>();
        if (cursor != null) {
            params.put("cursor", cursor);
        }
        if (size != null) {
            params.put("size", size);
        }
        if (year != null) {
            params.put("year", year);
        }
        return params;
    }
}
