package bankmonitor.repository;

import bankmonitor.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    List<Transaction> findAll();

    Optional<Transaction> findById(long id);

    Transaction save(Transaction transaction);

}
