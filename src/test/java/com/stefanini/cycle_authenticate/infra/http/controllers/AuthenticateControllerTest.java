package com.stefanini.cycle_authenticate.infra.http.controllers;

import com.stefanini.cycle_authenticate.ContainersConfiguration;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.JpaUserModelRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class AuthenticateControllerTest extends ContainersConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaUserModelRepository jpaUserModelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("should create a new user")
    public void should_create_a_new_user() throws Exception {
        String dateString = LocalDate.now().minusYears(18).toString(); // Gera 'yyyy-MM-dd'

        String body = String.format("""
            {
                "username": "jhon_doe",
                "email": "jhon_doe@example.com",
                "password": "Jhondoe2006@2025",
                "dateOfBirth": "%s",
                "userRole": "USER"
            }
        """, dateString);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertEquals(1, this.jpaUserModelRepository.findAll().size());

    }

    @Test
    @DisplayName("should not create a new user if already exists")
    public void should_not_create_a_new_user_if_already_exists() throws Exception {
        this.jpaUserModelRepository.save(new UserModel("jhon_doe", "jhon_doe@example.com", "Jhondoe2006@2025", LocalDate.now().minusYears(18)));


        String dateString = LocalDate.now().minusYears(18).toString(); // Gera 'yyyy-MM-dd'

        String body = String.format("""
            {
                "username": "jhon_doe",
                "email": "jhon_doe@example.com",
                "password": "Jhondoe2006@2025",
                "dateOfBirth": "%s",
                "userRole": "USER"
            }
        """, dateString);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
        List<UserModel> users = this.jpaUserModelRepository.findAll();
        Assertions.assertEquals(1, users.size());
        System.out.println(users);
    }

    @Test
    @DisplayName("should authenticate user")
    public void should_authenticate_user() throws Exception {
        String password = "Jhondoe2006@202";
        UserModel userModel =new UserModel("jhon_doe", "jhon_doe@example.com", this.passwordEncoder.encode(password) , LocalDate.now().minusYears(18));
        userModel = this.jpaUserModelRepository.save(userModel);

        String body = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
        """,userModel.getEmail(), password);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("should not authenticate if user not found")
    public void should_not_authenticate_if_user_not_found() throws Exception {
        String password = "Jhondoe2006@202";
        UserModel userModel =new UserModel("jhon_doe", "jhon_doe@example.com", this.passwordEncoder.encode(password) , LocalDate.now().minusYears(18));

        String body = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
        """,userModel.getEmail(), password);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("should not authenticate if password incorrect")
    public void should_not_authenticate_if_password_incorrect() throws Exception {
        String password = "Jhondoe2006@202";
        UserModel userModel =new UserModel("jhon_doe", "jhon_doe@example.com", this.passwordEncoder.encode(password) , LocalDate.now().minusYears(18));
        userModel = this.jpaUserModelRepository.save(userModel);

        String body = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
        """,userModel.getEmail(), password.concat("-incorrect"));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
