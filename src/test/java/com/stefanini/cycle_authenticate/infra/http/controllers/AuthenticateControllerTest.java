package com.stefanini.cycle_authenticate.infra.http.controllers;

import com.stefanini.cycle_authenticate.ContainersConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AuthenticateControllerTest extends ContainersConfiguration {


    @BeforeEach
    public void setup() {
        System.out.println("http://localhost:" + port);
    }

    @Test
    public void testConnection(){
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }
}
