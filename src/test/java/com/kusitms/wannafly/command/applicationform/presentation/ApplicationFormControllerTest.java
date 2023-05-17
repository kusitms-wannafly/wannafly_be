package com.kusitms.wannafly.command.applicationform.presentation;

import com.kusitms.wannafly.command.applicationform.dto.FormStateRequest;
import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationFormControllerTest extends ControllerTest {

    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 지원서를_등록한다() throws Exception {
        // given
        given(applicationFormService.createForm(any(), any()))
                .willReturn(1L);

        // when
        ResultActions result = mockMvc.perform(post("/api/application-forms")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(objectMapper.writeValueAsString(FORM_CREATE_REQUEST)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/application-forms/1"))

                .andDo(document("create-application-form", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("recruiter").type(JsonFieldType.STRING).description("동아리 명"),
                                fieldWithPath("year").type(JsonFieldType.NUMBER).description("지원 년도"),
                                fieldWithPath("semester").type(JsonFieldType.STRING).description("지원 분기"),
                                fieldWithPath("applicationItems[].applicationQuestion")
                                        .type(JsonFieldType.STRING).description("지원 문항"),
                                fieldWithPath("applicationItems[].applicationAnswer")
                                        .type(JsonFieldType.STRING).description("지원 답변")
                        )
                ));
    }

    @Test
    void 지원서를_수정한다() throws Exception {
        // when
        ResultActions result = mockMvc.perform(patch("/api/application-forms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(objectMapper.writeValueAsString(FORM_UPDATE_REQUEST)));

        // then
        result.andExpect(status().isNoContent())

                .andDo(document("update-application-form", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("recruiter").type(JsonFieldType.STRING).description("동아리 명"),
                                fieldWithPath("year").type(JsonFieldType.NUMBER).description("지원 년도"),
                                fieldWithPath("semester").type(JsonFieldType.STRING).description("지원 분기"),
                                fieldWithPath("applicationItems[].applicationItemId")
                                        .type(JsonFieldType.NUMBER).description("지원 항목 식별자"),
                                fieldWithPath("applicationItems[].applicationQuestion")
                                        .type(JsonFieldType.STRING).description("지원 문항"),
                                fieldWithPath("applicationItems[].applicationAnswer")
                                        .type(JsonFieldType.STRING).description("지원 답변")
                        )
                ));
    }

    @Test
    void 지원_항목을_추가한다() throws Exception {
        // given
        given(applicationFormService.addItem(any(), any(), any()))
                .willReturn(4L);

        // when
        ResultActions result = mockMvc.perform(post("/api/application-forms/1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(objectMapper.writeValueAsString(ITEM_CREATE_REQUEST)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/application-items/4"))

                .andDo(document("add-application-item", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("applicationQuestion").type(JsonFieldType.STRING).description("지원 문항"),
                                fieldWithPath("applicationAnswer").type(JsonFieldType.STRING).description("지원 답변")
                        )
                ));
    }

    @Test
    void 지원서를_삭제한다() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/api/application-forms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isNoContent())

                .andDo(document("delete-application-form", HOST_INFO,
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void 지원서_상태를_변경한다() throws Exception {
        // when
        ResultActions result = mockMvc.perform(patch("/api/application-forms/1/state")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FormStateRequest(true)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isNoContent())

                .andDo(document("application-form-state", HOST_INFO,
                        preprocessResponse(prettyPrint())
                ));
    }
}
