package com.kusitms.wannafly.applicationfolder.application;

import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.support.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2022;
import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2023;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class ApplicationFolderCheckControllerTest extends ControllerTest {
    private String accessToken;

    @BeforeEach
    void setToken(){
        accessToken = loginAndGetAccessToken(1L);
    }

    @Test
    void 지원서_보관함을_조회한다() throws Exception {
        // given
        given(applicationFolderService.createFolder(any(), any()))
                .willReturn(1L);

        // when
        ResultActions result = mockMvc.perform(get("/api/application-folders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        // then
        result.andExpect(status().isOk())
                .andDo(document("get-one-application-folder", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("year").type(JsonFieldType.NUMBER).description("지원 년도")
                        )
                ));
    }
}
