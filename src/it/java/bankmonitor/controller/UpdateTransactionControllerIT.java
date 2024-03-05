package bankmonitor.controller;

import bankmonitor.exception.ApiException;
import bankmonitor.model.Transaction;
import bankmonitor.service.UpdateTransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN update transaction API endpoint")
@SpringBootTest
@AutoConfigureMockMvc
class UpdateTransactionControllerIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UpdateTransactionService service;

    @DisplayName("WHEN calling with non-existing id THEN bad request is returned")
    @Test
    void whenUpdatingNonExistingTransactionThenShouldReturnBadRequest() throws Exception {
        var id = 42L;
        var jsonData = "{}";
        when(service.updateTransaction(eq(id), any(), any())).thenThrow(ApiException.class);

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("WHEN calling with amount THEN the transaction is updated")
    @Test
    void shouldUpdateAmount() throws Exception {
        var id = 1L;
        Integer amount = 24;
        String reference = null;
        var updateData = "{ \"amount\": 24 }";

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(service).updateTransaction(id, amount, reference);
    }

    @DisplayName("WHEN calling with reference THEN the transaction is updated")
    @Test
    void shouldUpdateReference() throws Exception {
        var id = 1L;
        Integer amount = null;
        String reference = "cat";
        var updateData = "{ \"reference\": \"cat\" }";

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(service).updateTransaction(id, amount, reference);
    }

    @DisplayName("WHEN calling THEN returns updated transaction")
    @Test
    void shouldReturnUpdatedTransaction() throws Exception {
        var id = 1L;
        Integer amount = 24;
        String reference = "dog";
        when(service.updateTransaction(anyLong(), any(), any())).thenReturn(Transaction.of(amount, reference));
        var updateData = "{ }";

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.reference").value(reference));
    }

}
