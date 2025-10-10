package com.stefanini.cycle_authenticate.infra.http.controllers;


import com.stefanini.cycle_authenticate.ContainersConfiguration;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.JpaUserModelRepository;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.security.SessionTokenServiceAdapter;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest( webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GetProfileControllerTest extends ContainersConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaUserModelRepository jpaUserModelRepository;

    @Autowired
    private SessionTokenServiceAdapter sessionTokenServiceAdapter;

    @Test
    @DisplayName("should be able return profile user")
    public void should_be_able_return_profile_user() throws Exception {
        UserModel  userModel = new UserModel("jhon_doe", "jhon_doe@gmail.com", "Jhondoe2026@2025", LocalDate.now().minusYears(18));
        userModel = this.jpaUserModelRepository.save(userModel);

        String token = this.sessionTokenServiceAdapter.generator(userModel.getId()).getToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/me").header("Authorization", "Bearer %s".formatted(token))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("should be able not return profile user if not exists")
    public void should_be_able_not_return_profile_user_if_not_exists() throws Exception {
        String token = this.sessionTokenServiceAdapter.generator(UUID.randomUUID()).getToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/me").header("Authorization", "Bearer %s".formatted(token))
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
