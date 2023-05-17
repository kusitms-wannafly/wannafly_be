package com.kusitms.wannafly.command.auth.presentation;

import com.kusitms.wannafly.command.auth.dto.ReIssueResponse;
import com.kusitms.wannafly.support.ControllerTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest extends ControllerTest {


    @Test
    void 토큰을_재발급_한다() throws Exception {
        // given
        String refreshTokenValue = "prev-refresh-token";
        String reIssuedRefreshTokenValue = "re-issued-refresh-token";
        ReIssueResponse expected = new ReIssueResponse(
                1L,
                "re-issued-access-token",
                reIssuedRefreshTokenValue
        );
        given(authService.reIssueTokens(refreshTokenValue))
                .willReturn(expected);

        // when
        ResultActions result = mockMvc.perform(post("/accessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(buildCookie(refreshTokenValue)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andExpect(cookie().value("refreshToken", reIssuedRefreshTokenValue))

                .andDo(document("re-issue-tokens", HOST_INFO,
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("재발급된 엑세스 토큰")
                        )));
    }

    private Cookie buildCookie(String refreshTokenValue) {
        Cookie cookie = new Cookie("refreshToken", refreshTokenValue);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Test
    void 로그아웃으로_리프레시_토큰을_삭제한다() throws Exception {
        // given
        String refreshTokenValue = "prev-refresh-token";

        // when
        ResultActions result = mockMvc.perform(delete("/refreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(buildCookie(refreshTokenValue)));

        // then
        result.andExpect(status().isNoContent())

                .andDo(document("delete-refresh-token", HOST_INFO,
                        preprocessResponse(prettyPrint())));
    }
}
