package com.stefanini.cycle_authenticate.infra.http.controllers;

import com.stefanini.cycle_authenticate.TestcontainersConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
public class ContainerControllerTest {

    @Autowired
    PostgreSQLContainer<?> postgreSQLContainer;

    @Test
    public void load(){
        Assertions.assertTrue(this.postgreSQLContainer.isCreated());
        Assertions.assertTrue(this.postgreSQLContainer.isRunning());
    }
}
