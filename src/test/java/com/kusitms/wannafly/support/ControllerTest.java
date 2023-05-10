package com.kusitms.wannafly.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.applicationform.application.ApplicationFormService;
import com.kusitms.wannafly.applicationform.presentation.ApplicationFormController;
import com.kusitms.wannafly.auth.AuthConfig;
import com.kusitms.wannafly.auth.LoginMemberResolver;
import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.auth.presentation.AuthController;
import com.kusitms.wannafly.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.auth.token.TokenPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebMvcTest(controllers = {
        AuthController.class,
        ApplicationFormController.class
})
@Import({
        ControllerTestSecurityConfig.class,
        AuthConfig.class,
        LoginMemberResolver.class,
        JwtTokenProvider.class,
})
@AutoConfigureRestDocs
public class ControllerTest {

    protected static final OperationRequestPreprocessor HOST_INFO = preprocessRequest(modifyUris()
            .scheme("https")
            .host("www.api.wannafly.co.kr")
            .removePort(), prettyPrint()
    );

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected ApplicationFormService applicationFormService;

    protected String loginAndGetAccessToken(Long memberId) {
        given(authService.authorize(any()))
                .willReturn(new AuthorizationResponse(true, memberId));
        return jwtTokenProvider.createToken(new TokenPayload(memberId));
    }
}
