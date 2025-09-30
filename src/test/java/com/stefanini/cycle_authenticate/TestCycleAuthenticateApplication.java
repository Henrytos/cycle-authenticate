package com.stefanini.cycle_authenticate;

import org.springframework.boot.SpringApplication;

public class TestCycleAuthenticateApplication {

	public static void main(String[] args) {
		SpringApplication.from(CycleAuthenticateApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
