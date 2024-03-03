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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GIVEN transaction API WHEN creating a transaction")
@SpringBootTest
@AutoConfigureMockMvc
class CreateTransactionsIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionRepository transactionRepository;

    @DisplayName("THEN it is saved")
    @Test
    void shouldReturnSaveTransaction() throws Exception {
        var jsonData = "{ \"reference\": \"bar\", \"amount\": 200 }";
        var transaction = new Transaction(jsonData);
        when(transactionRepository.save(any())).thenReturn(transaction);

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk());

        verify(transactionRepository).save(any());
    }

    @DisplayName("THEN the saved transaction is returned")
    @Test
    void shouldReturnSavedTransaction() throws Exception {
        var jsonData = "{ \"reference\": \"bar\", \"amount\": 200 }";
        var transaction = new Transaction(jsonData);
        when(transactionRepository.save(any())).thenReturn(transaction);

        mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonData)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.reference").value("bar"))
                .andExpect(jsonPath("$.amount").value(200))
                .andExpect(jsonPath("$.data").value(jsonData));
    }

}
