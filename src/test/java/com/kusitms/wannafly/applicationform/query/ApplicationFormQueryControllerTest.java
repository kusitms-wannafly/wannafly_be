package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.support.ControllerTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationFormQueryControllerTest extends ControllerTest {

    private String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 나의_지원서를_조회한다() throws Exception {
        // given
        List<ApplicationItemResponse> items = List.of(
                new ApplicationItemResponse(1L, ApplicationFormText.QUESTION, ApplicationFormText.ANSWER),
                new ApplicationItemResponse(2L, ApplicationFormText.QUESTION, ApplicationFormText.ANSWER),
                new ApplicationItemResponse(3L, ApplicationFormText.QUESTION, ApplicationFormText.ANSWER)
        );
        given(applicationFormQueryService.findOne(any(), any()))
                .willReturn(new ApplicationFormResponse("큐시즘", 2023, "first_half", items));


        // when
        ResultActions result = mockMvc.perform(get("/api/application-forms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isOk())

                .andDo(document("get-one-application-form", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        responseFields(
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
}
