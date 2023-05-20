package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.command.category.dto.CategoryCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.kusitms.wannafly.Acceptance.fixture.CategoryAcceptanceFixture.*;
import static com.kusitms.wannafly.support.fixture.CategoryFixture.CATEGORY_CREATE_MOTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryAcceptanceTest extends AcceptanceTest {


    @Test
    void 원하는_카테고리를_생성한다() {
        //when
        ExtractableResponse<Response> response = 카테고리를_생성한다(accessToken, CATEGORY_CREATE_MOTIVE);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header(HttpHeaders.LOCATION)).isEqualTo("/categories/" + 1)
        );
    }

    @Test
    void 자신의_카테고리들을_조회한다() {
        //given
        카테고리를_생성한다(accessToken, CATEGORY_CREATE_MOTIVE);

        //when
        ExtractableResponse<Response> response = 카테고리를_조회한다(accessToken);

        //then
        int categoryId = response.body().jsonPath().getInt("[0].categoryId");
        String name = response.body().jsonPath().getString("[0].name");
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(categoryId).isEqualTo(1),
                () -> assertThat(name).isEqualTo("지원동기")
        );
    }

    @Test
    void 나의_카테고리를_삭제한다() {
        // given
        Long categoryId = 카테고리를_등록하고_ID를_응답(accessToken, CATEGORY_CREATE_MOTIVE);

        // when
        ExtractableResponse<Response> response = 카테고리를_삭제한다(accessToken, categoryId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public Long 카테고리를_등록하고_ID를_응답(String accessToken, CategoryCreateRequest request) {
        return extractCreatedId(카테고리를_생성한다(accessToken, request));
    }

    private long extractCreatedId(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header(HttpHeaders.LOCATION).split("/")[2]);
    }
}
