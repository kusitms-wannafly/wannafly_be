package com.kusitms.wannafly.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.command.applicationform.application.ApplicationItemService;
import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.command.grammar.presentation.GrammarController;
import com.kusitms.wannafly.query.service.ApplicationFolderQueryService;
import com.kusitms.wannafly.command.applicationfolder.presentation.ApplicationFolderController;
import com.kusitms.wannafly.query.controller.ApplicationFolderQueryController;
import com.kusitms.wannafly.command.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.command.applicationform.application.ApplicationFormService;
import com.kusitms.wannafly.command.applicationform.presentation.ApplicationFormController;
import com.kusitms.wannafly.query.controller.ApplicationFormQueryController;
import com.kusitms.wannafly.query.service.ApplicationFormQueryService;
import com.kusitms.wannafly.command.auth.AuthConfig;
import com.kusitms.wannafly.command.auth.LoginMemberResolver;
import com.kusitms.wannafly.command.auth.application.AuthService;
import com.kusitms.wannafly.command.auth.dto.AuthorizationResponse;
import com.kusitms.wannafly.command.auth.presentation.AuthController;
import com.kusitms.wannafly.command.auth.token.JwtTokenProvider;
import com.kusitms.wannafly.command.auth.token.TokenPayload;
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
        ApplicationFormController.class,
        ApplicationFormQueryController.class,
        ApplicationFolderController.class,
        ApplicationFolderQueryController.class,
        GrammarController.class
})
@Import({
        ControllerTestSecurityConfig.class,
        AuthConfig.class,
        LoginMemberResolver.class,
        JwtTokenProvider.class
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

    @MockBean
    protected ApplicationFormQueryService applicationFormQueryService;

    @MockBean
    protected ApplicationFolderService applicationFolderService;

    @MockBean
    protected ApplicationItemService applicationItemService;

    @MockBean
    protected ApplicationFolderQueryService applicationFolderQueryService;

    @MockBean
    protected GrammarService grammarService;

    protected String loginAndGetAccessToken(Long memberId) {
        given(authService.authorize(any()))
                .willReturn(new AuthorizationResponse(true, memberId));
        return jwtTokenProvider.createToken(new TokenPayload(memberId));
    }
}
