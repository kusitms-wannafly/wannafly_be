package com.kusitms.wannafly.applicationform.command.presentation;

import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.KUSITMS_FORM_REQUEST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .content(objectMapper.writeValueAsString(KUSITMS_FORM_REQUEST)));

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
}
