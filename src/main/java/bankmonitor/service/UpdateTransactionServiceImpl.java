package bankmonitor.service;

import bankmonitor.exception.ApiException;
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
class UpdateTransactionServiceImpl implements UpdateTransactionService {

    TransactionRepository transactionRepository;

    @CacheEvict(cacheNames = "transactions", allEntries = true)
    @Override
    public Transaction updateTransaction(long id, Integer amount, String reference) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException(String.format("Transaction not found with id '%d'", id)));

        log.info("Updating transaction with id '{}'", id);
        if (amount != null) {
            log.debug("New amount: {}", amount);
            transaction.setAmount(amount);
        }

        if (reference != null) {
            log.debug("New reference: '{}'", reference);
            transaction.setReference(reference);
        }

        return transactionRepository.save(transaction);
    }
}
