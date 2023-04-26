package com.kusitms.wannafly;

import com.kusitms.wannafly.support.isolation.WannaflyTestIsolationExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(WannaflyTestIsolationExtension.class)
class WannaflyApplicationTests {

	@Test
	void contextLoads() {
	}

}
