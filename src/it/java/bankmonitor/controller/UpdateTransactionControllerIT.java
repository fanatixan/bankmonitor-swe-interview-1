package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    TransactionRepository transactionRepository;

    @DisplayName("WHEN calling with non-existing id THEN bad request is returned")
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

    @DisplayName("WHEN calling with amount THEN the transaction is updated")
    @Test
    void shouldUpdateAmount() throws Exception {
        var id = 1L;
        var oldTransaction = Transaction.of(13, "dog");
        when(transactionRepository.findById(id)).thenReturn(Optional.of(oldTransaction));

        var updateData = "{ \"amount\": 24 }";

        var newTransaction = Transaction.of(24, "dog");
        when(transactionRepository.save(any())).thenReturn(newTransaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("dog"))
                .andExpect(jsonPath("$.amount").value(24));

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(24);
        assertThat(savedTransaction.getReference()).isEqualTo("dog");
    }

    @DisplayName("WHEN calling with reference THEN the transaction is updated")
    @Test
    void shouldUpdateReference() throws Exception {
        var id = 2L;
        var oldTransaction = Transaction.of(13, "dog");
        when(transactionRepository.findById(id)).thenReturn(Optional.of(oldTransaction));

        var updateData = "{ \"reference\": \"cat\" }";

        var newTransaction = Transaction.of(13, "cat");
        when(transactionRepository.save(any())).thenReturn(newTransaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

        mockMvc.perform(
                        put("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("cat"))
                .andExpect(jsonPath("$.amount").value(13));

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(13);
        assertThat(savedTransaction.getReference()).isEqualTo("cat");
    }

}
