package bankmonitor.service;

import bankmonitor.model.Transaction;

import java.util.List;

public interface GetAllTransactionsService {

    List<Transaction> findAll();

}
