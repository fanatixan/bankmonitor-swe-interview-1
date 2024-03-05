package bankmonitor.service;

import bankmonitor.model.Transaction;

public interface CreateTransactionService {

    Transaction createTransaction(Integer amount, String reference);

}
