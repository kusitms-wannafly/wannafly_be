package com.kusitms.wannafly.command.grammar.presentation;

import com.kusitms.wannafly.command.grammar.dto.GrammarRequest;
import com.kusitms.wannafly.command.grammar.dto.GrammarResponse;
import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GrammarControllerTest extends ControllerTest {

    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 문법을_체크한() throws Exception {
        // given
        BDDMockito.given(grammarService.check(any()))
                .willReturn(new GrammarResponse("왜 안 돼"));

        // when
        ResultActions result = mockMvc.perform(post("/api/grammar/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new GrammarRequest("외않되")))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isOk())

                .andDo(document("grammar-check", HOST_INFO,
                        preprocessResponse(prettyPrint()),

                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("검사 전 텍스트")
                        ),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("검사 후 텍스트")
                        )
                ));
    }
}
