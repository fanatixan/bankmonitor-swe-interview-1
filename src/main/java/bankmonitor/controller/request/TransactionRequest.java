package bankmonitor.controller.request;

import bankmonitor.model.Transaction;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TransactionRequest {
    Integer amount;
    String reference;

    public Transaction toTransaction() {
        return Transaction.of(amount, reference);
    }
}
