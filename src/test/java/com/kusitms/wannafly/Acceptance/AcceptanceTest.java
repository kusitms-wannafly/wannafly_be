package com.kusitms.wannafly.Acceptance;

import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(WannaflyTestIsolationExtension.class)
class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private GrammarService grammarService;
}
