package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.service.GetAllTransactionsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN get all transactions API endpoint")
@SpringBootTest
@AutoConfigureMockMvc
class GetAllTransactionsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GetAllTransactionsService service;

    @DisplayName("WHEN calling it THEN all transactions in the system are returned")
    @Test
    void shouldReturnAllTransactions() throws Exception {
        when(service.findAll()).thenReturn(List.of(Transaction.of(100, "foo")));

        mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].reference").value("foo"))
                .andExpect(jsonPath("$[0].amount").value(100));
    }

}
