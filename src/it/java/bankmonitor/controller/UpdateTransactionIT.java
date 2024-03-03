package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN transaction API")
@SpringBootTest
@AutoConfigureMockMvc
class UpdateTransactionIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionRepository transactionRepository;

    @DisplayName("WHEN updating non-existing transaction THEN bad request is returned")
    @Test
    void whenUpdatingNonExistingTransactionThenShouldReturnBadRequest() throws Exception {
        var id = 42L;
        var jsonData = "{}";
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("WHEN updating a transaction's amount THEN the transaction is updated")
    @Test
    void shouldUpdateAmount() throws Exception {
        var id = 1L;
        var oldData = "{ \"reference\": \"dog\", \"amount\": 13 }";
        var oldTransaction = new Transaction(oldData);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(oldTransaction));

        var updateData = "{ \"amount\": 24 }";
        var newData = "{ \"reference\": \"dog\", \"amount\": 24 }";

        var newTransaction = new Transaction(newData);
        when(transactionRepository.save(any())).thenReturn(newTransaction);

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("dog"))
                .andExpect(jsonPath("$.amount").value(24))
                .andExpect(jsonPath("$.data").value(newData));

        verify(transactionRepository).save(any());
    }

    @DisplayName("WHEN updating a transaction's reference THEN the transaction is updated")
    @Test
    void shouldUpdateReference() throws Exception {
        var id = 2L;
        var oldData = "{ \"reference\": \"dog\", \"amount\": 13 }";
        var oldTransaction = new Transaction(oldData);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(oldTransaction));

        var updateData = "{ \"reference\": \"cat\" }";
        var newData = "{ \"reference\": \"cat\", \"amount\": 13 }";

        var newTransaction = new Transaction(newData);
        when(transactionRepository.save(any())).thenReturn(newTransaction);

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("cat"))
                .andExpect(jsonPath("$.amount").value(13))
                .andExpect(jsonPath("$.data").value(newData));

        verify(transactionRepository).save(any());
    }

}
