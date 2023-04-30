package com.kusitms.wannafly.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.wannafly.auth.application.AuthService;
import com.kusitms.wannafly.auth.presentation.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebMvcTest(controllers = AuthController.class)
@Import(ControllerTestSecurityConfig.class)
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
}
