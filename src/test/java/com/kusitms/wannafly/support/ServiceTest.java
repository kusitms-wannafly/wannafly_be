package com.kusitms.wannafly.support;

import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(WannaflyTestIsolationExtension.class)
public class ServiceTest {
}
