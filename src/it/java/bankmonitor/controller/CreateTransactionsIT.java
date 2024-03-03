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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN transaction API")
@SpringBootTest
@AutoConfigureMockMvc
class CreateTransactionsIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionRepository transactionRepository;

    @DisplayName("WHEN creating a transaction with amount and reference THEN the saved transaction is returned")
    @Test
    void whenPassingAmountAndReferenceThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = "{ \"reference\": \"bar\", \"amount\": 200 }";
        var transaction = Transaction.of(200, "bar");
        when(transactionRepository.save(any())).thenReturn(transaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

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

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(200);
        assertThat(savedTransaction.getReference()).isEqualTo("bar");
    }

    @DisplayName("WHEN creating a transaction with amount only THEN the saved transaction is returned")
    @Test
    void whenPassingAmountOnlyThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = "{ \"amount\": 200 }";
        var transaction = Transaction.of(200, "");
        when(transactionRepository.save(any())).thenReturn(transaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

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

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(200);
        assertThat(savedTransaction.getReference()).isEmpty();
    }

    @DisplayName("WHEN creating a transaction with reference only THEN the saved transaction is returned")
    @Test
    void whenPassingReferenceOnlyThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = "{ \"reference\": \"bar\" }";
        var transaction = Transaction.of(-1, "bar");
        when(transactionRepository.save(any())).thenReturn(transaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

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

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(-1);
        assertThat(savedTransaction.getReference()).isEqualTo("bar");
    }

    @DisplayName("WHEN creating a transaction with no amount or reference THEN the saved transaction is returned")
    @Test
    void whenPassingNoAmountOrReferenceThenShouldReturnSavedTransaction() throws Exception {
        var jsonData = "{ }";
        var transaction = Transaction.of(-1, "");
        when(transactionRepository.save(any())).thenReturn(transaction);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);

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

        verify(transactionRepository).save(transactionCaptor.capture());
        var savedTransaction = transactionCaptor.getValue();
        assertThat(savedTransaction.getAmount()).isEqualTo(-1);
        assertThat(savedTransaction.getReference()).isEmpty();
    }

}
