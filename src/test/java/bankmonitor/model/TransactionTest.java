package bankmonitor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Transaction")
class TransactionTest {

    @DisplayName("GIVEN amount and reference WHEN creating a transaction THEN both are filled")
    @Test
    void givenAmountAndReferenceWhenCreatingTransactionThenBothAreFilled() {
        // given
        Integer amount = 5;
        String reference = "foo";

        // when
        var transaction = Transaction.of(amount, reference);

        // then
        assertThat(transaction.getAmount()).isEqualTo(5);
        assertThat(transaction.getReference()).isEqualTo("foo");
    }

    @DisplayName("GIVEN amount WHEN creating a transaction THEN amount is filled AND reference has default empty value")
    @Test
    void givenAmountWhenCreatingTransactionThenAmountIsFilledAndReferenceIsEmptyByDefault() {
        // given
        Integer amount = 5;
        String reference = null;

        // when
        var transaction = Transaction.of(amount, reference);

        // then
        assertThat(transaction.getAmount()).isEqualTo(5);
        assertThat(transaction.getReference()).isEmpty();
    }

    @DisplayName("GIVEN reference WHEN creating a transaction THEN reference is filled AND amount has default -1 value")
    @Test
    void givenReferenceWhenCreatingTransactionThenReferenceIsFilledAndAmountIsMinusOneByDefault() {
        // given
        Integer amount = null;
        String reference = "foo";

        // when
        var transaction = Transaction.of(amount, reference);

        // then
        assertThat(transaction.getAmount()).isEqualTo(-1);
        assertThat(transaction.getReference()).isEqualTo("foo");
    }

    @DisplayName("GIVEN reference WHEN creating a transaction THEN reference is filled AND amount has default -1 value")
    @Test
    void givenNoDataWhenCreatingTransactionThenBothAmountAndReferenceHaveDefaultValues() {
        // given
        Integer amount = null;
        String reference = null;

        // when
        var transaction = Transaction.of(amount, reference);

        // then
        assertThat(transaction.getAmount()).isEqualTo(-1);
        assertThat(transaction.getReference()).isEmpty();
    }

}
