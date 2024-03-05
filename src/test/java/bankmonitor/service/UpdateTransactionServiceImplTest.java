package bankmonitor.service;

import bankmonitor.exception.ApiException;
import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Update transaction")
@ExtendWith(MockitoExtension.class)
class UpdateTransactionServiceImplTest {

    UpdateTransactionService service;

    @Spy
    TransactionRepositoryStub transactionRepository;

    @BeforeEach
    void setup() {
        service = new UpdateTransactionServiceImpl(transactionRepository);
    }

    @DisplayName("GIVEN non-existing transaction ID WHEN updating transaction THEN exception is thrown")
    @Test
    void givenNonExistingIdWhenUpdatingTransactionThenExceptionIsThrown() {
        // given
        long id = 42;
        Integer amount = 5;
        String reference = "bar";
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        // when
        ThrowableAssert.ThrowingCallable update = () -> service.updateTransaction(id, amount, reference);

        // then
        assertThatThrownBy(update).isInstanceOf(ApiException.class);
    }

    @DisplayName("GIVEN existing transaction ID and amount and reference WHEN updating transaction THEN amount and reference are updated")
    @Test
    void givenExistingIdAndAmountAndReferenceWhenUpdatingTransactionThenAmountAndReferenceAreUpdated() {
        // given
        long id = 1;
        Integer originalAmount = 2;
        String originalReference = "baz";
        var originalTransaction = Transaction.of(originalAmount, originalReference);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(originalTransaction));

        Integer newAmount = 5;
        String newReference = "foo";

        // when
        Transaction updatedTransaction = service.updateTransaction(id, newAmount, newReference);

        // then
        assertThat(updatedTransaction.getAmount()).isEqualTo(newAmount);
        assertThat(updatedTransaction.getReference()).isEqualTo(newReference);
        verify(transactionRepository).save(any());
    }

    @DisplayName("GIVEN existing transaction ID and amount WHEN updating transaction THEN only amount is updated")
    @Test
    void givenExistingIdAndAmountWhenUpdatingTransactionThenOnlyAmountIsUpdated() {
        // given
        long id = 1;
        Integer originalAmount = 2;
        String originalReference = "baz";
        var originalTransaction = Transaction.of(originalAmount, originalReference);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(originalTransaction));

        Integer newAmount = 5;
        String newReference = null;

        // when
        Transaction updatedTransaction = service.updateTransaction(id, newAmount, newReference);

        // then
        assertThat(updatedTransaction.getAmount()).isEqualTo(newAmount);
        assertThat(updatedTransaction.getReference()).isEqualTo(originalReference);
        verify(transactionRepository).save(any());
    }

    @DisplayName("GIVEN existing transaction ID and reference WHEN updating transaction THEN only reference is updated")
    @Test
    void givenExistingIdAndReferenceWhenUpdatingTransactionThenOnlyReferenceIsUpdated() {
        // given
        long id = 1;
        Integer originalAmount = 2;
        String originalReference = "baz";
        var originalTransaction = Transaction.of(originalAmount, originalReference);
        when(transactionRepository.findById(id)).thenReturn(Optional.of(originalTransaction));

        Integer newAmount = null;
        String newReference = "noidea";

        // when
        Transaction updatedTransaction = service.updateTransaction(id, newAmount, newReference);

        // then
        assertThat(updatedTransaction.getAmount()).isEqualTo(originalAmount);
        assertThat(updatedTransaction.getReference()).isEqualTo(newReference);
        verify(transactionRepository).save(any());
    }

    static abstract class TransactionRepositoryStub implements TransactionRepository {
        @Override
        public Transaction save(Transaction entity) {
            return entity;
        }
    }

}
