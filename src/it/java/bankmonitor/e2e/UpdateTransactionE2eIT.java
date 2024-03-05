package bankmonitor.e2e;

import bankmonitor.IntegrationTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN update transaction API endpoint")
@AutoConfigureMockMvc
class UpdateTransactionE2eIT extends IntegrationTestContext {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("WHEN calling with non-existing id THEN bad request is returned")
    @Test
    void whenUpdatingNonExistingTransactionThenShouldReturnBadRequest() throws Exception {
        var id = -5000L;
        var jsonData = "{}";

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("WHEN calling with amount and reference THEN the transaction is updated")
    @DirtiesContext
    @Test
    void shouldUpdateAmount() throws Exception {
        var id = 1L;
        var updateData = """
                {
                  "amount": 31415,
                  "reference": "pi"
                }
                """;

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.reference").value("pi"))
                .andExpect(jsonPath("$.amount").value(31415));
    }

}
