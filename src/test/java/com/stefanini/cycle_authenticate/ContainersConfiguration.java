package com.stefanini.cycle_authenticate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class ContainersConfiguration {

  @Container
    protected static  final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

  @BeforeAll
  static void upContainer(){
      postgreSQLContainer.start();
  }

  @AfterAll
  static void stopContainer(){
      postgreSQLContainer.stop();
  }

  @DynamicPropertySource
    static void configureProperties(
            DynamicPropertyRegistry registry
  ){
      System.out.println(postgreSQLContainer.getUsername());
      System.out.println(postgreSQLContainer.getPassword());
      System.out.println(postgreSQLContainer.getJdbcUrl());

      registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
      registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
      registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
  }

}
