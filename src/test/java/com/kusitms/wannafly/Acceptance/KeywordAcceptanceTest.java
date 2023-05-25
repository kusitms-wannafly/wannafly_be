package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kusitms.wannafly.Acceptance.fixture.ApplicationFormAcceptanceFixture.나의_지원서를_조회한다;
import static com.kusitms.wannafly.Acceptance.fixture.ApplicationFormAcceptanceFixture.지원서를_등록한다;
import static com.kusitms.wannafly.Acceptance.fixture.KeywordAcceptanceFixture.키워드로_지원항목을_조회한다;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST_KEYWORD;
import static org.assertj.core.api.Assertions.assertThat;

public class KeywordAcceptanceTest extends AcceptanceTest {
    @Test
    void 키워드를_통해_지원항목을_조회한다() {
        //given
        Long formId = 지원서를_등록하고_ID를_응답(accessToken, FORM_CREATE_REQUEST_KEYWORD);
        List<ApplicationItemResponse> items = 나의_지원서를_조회한다(accessToken, formId)
                .jsonPath()
                .getObject(".", ApplicationFormResponse.class)
                .applicationItems();
        Long itemId1 = items.get(0).applicationItemId();
        Long itemId2 = items.get(1).applicationItemId();

        // when
        ExtractableResponse<Response> response = 키워드로_지원항목을_조회한다(accessToken, "노력");

        // then
        List<CategoryItemResponse> actual = response.jsonPath().getList(".", CategoryItemResponse.class);
        assertThat(actual)
                .map(item -> item.applicationItem().applicationItemId())
                .containsOnly(itemId1, itemId2);
    }

    private Long 지원서를_등록하고_ID를_응답(String accessToken, ApplicationFormCreateRequest request) {
        return extractCreatedId(지원서를_등록한다(accessToken, request));
    }

    private long extractCreatedId(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header(HttpHeaders.LOCATION).split("/")[2]);
    }
}
