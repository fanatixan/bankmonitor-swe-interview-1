package bankmonitor.service;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
class CreateTransactionServiceImpl implements CreateTransactionService {

    TransactionRepository transactionRepository;

    @CacheEvict(cacheNames = "transactions", allEntries = true)
    @Override
    public Transaction createTransaction(Integer amount, String reference) {
        log.info("Creating new transaction for reference '{}'", reference);
        log.debug("New amount: {}", amount);
        var transaction = Transaction.of(amount, reference);
        return transactionRepository.save(transaction);
    }
}
