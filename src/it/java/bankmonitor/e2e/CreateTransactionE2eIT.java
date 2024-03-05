package bankmonitor.e2e;

import bankmonitor.IntegrationTestContext;
import bankmonitor.model.Transaction;
import bankmonitor.service.CreateTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN create transaction API endpoint")
@AutoConfigureMockMvc
class CreateTransactionE2eIT extends IntegrationTestContext {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("WHEN calling with amount and reference THEN the saved transaction is returned")
    @DirtiesContext
    @Test
    void whenCallingWithAmountAndReferenceThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = """
                {
                  "reference": "bar",
                  "amount": 200
                }
                """;

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("bar"))
                .andExpect(jsonPath("$.amount").value(200));
    }

}
