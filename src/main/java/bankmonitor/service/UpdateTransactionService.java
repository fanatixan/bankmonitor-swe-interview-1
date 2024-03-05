package bankmonitor.service;

import bankmonitor.model.Transaction;

public interface UpdateTransactionService {

    Transaction updateTransaction(long id, Integer amount, String reference);

}
