package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.TestcontainersConfiguration;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class JpaTransactionModelRepositoryTest {

    @Autowired
    private JpaUserModelRepository jpaUserModelRepository;

    @Autowired
    private JpaTransactionModelRepository jpaTransactionModelRepository;

    @Test
    @DisplayName("find All By Sender Id")
    void findAllBySenderId() {

        UserModel userModel = new UserModel("example","example@gmail.com", "example-password", LocalDate.now().minusYears(18));
        userModel = this.jpaUserModelRepository.save(userModel);

        this.jpaTransactionModelRepository.findAllBySenderId(userModel.getId());
    }

    @Test
    void findAllBySenderRecent() {
    }

    @Test
    void findThreeLargest() {
    }
}