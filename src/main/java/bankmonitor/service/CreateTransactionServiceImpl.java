package bankmonitor.service;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class CreateTransactionServiceImpl implements CreateTransactionService {

    TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Integer amount, String reference) {
        var transaction = Transaction.of(amount, reference);
        return transactionRepository.save(transaction);
    }
}
