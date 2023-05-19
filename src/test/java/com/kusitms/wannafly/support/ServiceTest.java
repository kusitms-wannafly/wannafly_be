package com.kusitms.wannafly.support;

import com.kusitms.wannafly.command.grammar.application.GrammarService;
import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(WannaflyTestIsolationExtension.class)
public class ServiceTest {

    @MockBean
    private GrammarService grammarService;
}
