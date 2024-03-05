package bankmonitor.e2e;

import bankmonitor.IntegrationTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN get all transactions API endpoint")
@AutoConfigureMockMvc
class GetAllTransactionsE2eIT extends IntegrationTestContext {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("WHEN calling it THEN all transactions in the system are returned")
    @Test
    void shouldReturnAllTransactions() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].reference").isString())
                .andExpect(jsonPath("$[0].amount").isNumber());
    }

}
