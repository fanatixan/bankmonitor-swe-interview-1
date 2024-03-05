package bankmonitor.controller;

import bankmonitor.IntegrationTestContext;
import bankmonitor.model.Transaction;
import bankmonitor.service.CreateTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN create transaction API endpoint")
@AutoConfigureMockMvc
class CreateTransactionControllerIT extends IntegrationTestContext {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CreateTransactionService service;

    @DisplayName("WHEN calling with amount and reference THEN the saved transaction is returned")
    @Test
    void whenCallingWithAmountAndReferenceThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = """
                {
                  "reference": "bar",
                  "amount": 200
                }
                """;
        var transaction = Transaction.of(200, "bar");
        when(service.createTransaction(200, "bar")).thenReturn(transaction);

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

        verify(service).createTransaction(200, "bar");
    }

    @DisplayName("WHEN calling with amount only THEN the saved transaction is returned")
    @Test
    void whenCallingWithAmountOnlyThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = """
                {
                  "amount": 200
                }
                 """;
        var transaction = Transaction.of(200, "");
        when(service.createTransaction(200, null)).thenReturn(transaction);

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value(""))
                .andExpect(jsonPath("$.amount").value(200));

        verify(service).createTransaction(200, null);
    }

    @DisplayName("WHEN calling with reference only THEN the saved transaction is returned")
    @Test
    void whenCallingWithReferenceOnlyThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = """
                {
                  "reference": "bar"
                }
                """;
        var transaction = Transaction.of(-1, "bar");
        when(service.createTransaction(null, "bar")).thenReturn(transaction);

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("bar"))
                .andExpect(jsonPath("$.amount").value(-1));

        verify(service).createTransaction(null, "bar");
    }

    @DisplayName("WHEN calling with no amount or reference THEN the saved transaction is returned")
    @Test
    void whenCallingWithNoAmountOrReferenceThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = "{}";
        var transaction = Transaction.of(-1, "");
        when(service.createTransaction(null, null)).thenReturn(transaction);

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value(""))
                .andExpect(jsonPath("$.amount").value(-1));

        verify(service).createTransaction(null, null);
    }

}
