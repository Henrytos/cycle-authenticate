package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.TestcontainersConfiguration;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.LargestExpensesDTO;
import com.stefanini.cycle_authenticate.application.services.TransactionsServiceImpl;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.security.SessionTokenServiceAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean; // <--- USAR MockBean
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionsServiceImpl transactionsServicePort;

    @Autowired
    private JacksonTester<List<LargestExpensesDTO>> largestExpensesDTOJacksonTester;

    @Test
    @DisplayName("should return status 200")
    @WithMockUser
    void should_return_status_200() throws Exception {

        String userId = UUID.randomUUID().toString();

        LargestExpensesDTO largestExpensesDTO = new LargestExpensesDTO(10.0, "example", 10);

        Mockito.when(this.transactionsServicePort.getThreeLargestExpenses(Mockito.any())).thenReturn(List.of(largestExpensesDTO));

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get(
                        "/transactions/three_biggest_expenses"
                ).requestAttr("userId", userId)).andReturn().getResponse();

        Assertions.assertTrue(response.getStatus() == 200);
        Assertions.assertEquals(this.largestExpensesDTOJacksonTester.write(List.of(largestExpensesDTO)).getJson(), response.getContentAsString());
    }
}