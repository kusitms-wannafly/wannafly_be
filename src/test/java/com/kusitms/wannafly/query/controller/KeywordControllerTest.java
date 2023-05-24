package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.ANSWER1;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.QUESTION1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class KeywordControllerTest extends ControllerTest {
    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 키워드로_지원항목을_조회한다() throws Exception {
        // given
        ApplicationItemResponse item1 = new ApplicationItemResponse(1L, QUESTION1, ANSWER1);
        ApplicationItemResponse item2 = new ApplicationItemResponse(2L, QUESTION1, ANSWER1);
        ApplicationItemResponse item3 = new ApplicationItemResponse(3L, QUESTION1, ANSWER1);
        given(keywordService.findByKeyword(any(), any()))
                .willReturn(List.of(
                                new CategoryItemResponse(item1, 1L, "큐시즘", 2023, "first_half"),
                                new CategoryItemResponse(item2, 1L, "큐시즘", 2023, "first_half"),
                                new CategoryItemResponse(item3, 2L, "soft", 2023, "first_half")
                        )
                );

        // when
        ResultActions result = mockMvc.perform(get("/api/application-items?keyword=백엔드")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isOk())

                .andDo(document("get-item-by-keyword", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].applicationItem.applicationItemId")
                                        .type(JsonFieldType.NUMBER).description("지원 항목 식별자"),
                                fieldWithPath("[].applicationItem.applicationQuestion")
                                        .type(JsonFieldType.STRING).description("지원 문항"),
                                fieldWithPath("[].applicationItem.applicationAnswer")
                                        .type(JsonFieldType.STRING).description("지원 답변"),

                                fieldWithPath("[].applicationFormId").type(JsonFieldType.NUMBER).description("지원서 식별자"),
                                fieldWithPath("[].recruiter").type(JsonFieldType.STRING).description("동아리 명"),
                                fieldWithPath("[].year").type(JsonFieldType.NUMBER).description("지원 년도"),
                                fieldWithPath("[].semester").type(JsonFieldType.STRING).description("지원 분기")
                        )
                ));
    }
}
