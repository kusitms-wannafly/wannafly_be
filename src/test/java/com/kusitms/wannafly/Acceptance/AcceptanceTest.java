package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.kusitms.wannafly.Acceptance.fixture.AuthAcceptanceFixture.소셜_로그인을_한다;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(WannaflyTestIsolationExtension.class)
class AcceptanceTest {

    protected String accessToken;

    @BeforeEach
    void setToken() {
        accessToken = 소셜_로그인을_한다("google")
                .jsonPath()
                .getString("accessToken");
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private GrammarService grammarService;
}
