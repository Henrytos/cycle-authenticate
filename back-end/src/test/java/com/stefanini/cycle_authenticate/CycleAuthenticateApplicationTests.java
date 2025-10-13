package com.stefanini.cycle_authenticate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CycleAuthenticateApplicationTests {

	@Test
	void contextLoads() {
	}

}
