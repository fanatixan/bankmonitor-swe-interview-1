package bankmonitor.service;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DisplayName("Create transaction")
@ExtendWith(MockitoExtension.class)
class CreateTransactionServiceImplTest {

    CreateTransactionService service;

    @Spy
    TransactionRepositoryStub transactionRepository;

    @BeforeEach
    void setup() {
        service = new CreateTransactionServiceImpl(transactionRepository);
    }

    @DisplayName("WHEN creating a transaction THEN it is saved to the database")
    @Test
    void test() {
        // given
        Integer amount = 10;
        String reference = "spiderpig";

        // when
        Transaction transaction = service.createTransaction(amount, reference);

        // then
        verify(transactionRepository).save(any());
        assertThat(transaction.getAmount()).isEqualTo(amount);
        assertThat(transaction.getReference()).isEqualTo(reference);
    }

    static abstract class TransactionRepositoryStub implements TransactionRepository {
        @Override
        public Transaction save(Transaction entity) {
            return entity;
        }
    }

}
