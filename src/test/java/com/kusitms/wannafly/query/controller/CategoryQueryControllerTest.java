package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.query.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.query.dto.CategoryResponse;
import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryQueryControllerTest extends ControllerTest {
    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 카테고리들을_조회한다() throws Exception {
        // given
        List<CategoryResponse> items = Arrays.asList(
                new CategoryResponse(1L, "지원동기"),
                new CategoryResponse(2L, "장단점"),
                new CategoryResponse(3L, "경험")
        );
        given(categoryQueryService.extractCategoryByMemberId(anyLong()))
                .willReturn(items);

        // when
        ResultActions result = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isOk())
                .andDo(document("get-one-category", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[]categoryId")
                                        .type(JsonFieldType.NUMBER)
                                        .description("카테고리 번호"),
                                fieldWithPath("[]name")
                                        .type(JsonFieldType.STRING)
                                        .description("카테고리 이름")
                        )
                ));
    }
}
