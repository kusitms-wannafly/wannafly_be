package com.kusitms.wannafly.command.category.presentaion;

import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2023;
import static com.kusitms.wannafly.support.fixture.CategoryFixture.CATEGORY_CREATE_MOTIVE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends ControllerTest {
    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 카테고리를_생성한다() throws Exception {
        // given
        given(categoryService.createCategory(any(), any()))
                .willReturn(1L);

        // when
        ResultActions result = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(objectMapper.writeValueAsString(CATEGORY_CREATE_MOTIVE)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/categories/1"))

                .andDo(document("create-category", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름")
                        )
                ));
    }

    @Test
    void 카테고리를_삭제한다() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isNoContent())

                .andDo(document("delete-categories", HOST_INFO,
                        preprocessResponse(prettyPrint())
                ));
    }
}
