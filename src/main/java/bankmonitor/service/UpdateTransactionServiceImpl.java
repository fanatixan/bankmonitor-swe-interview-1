package bankmonitor.service;

import bankmonitor.exception.ApiException;
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
class UpdateTransactionServiceImpl implements UpdateTransactionService {

    TransactionRepository transactionRepository;

    @Override
    public Transaction updateTransaction(long id, Integer amount, String reference) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException(String.format("Transaction not found with id '%d'", id)));

        if (amount != null) {
            transaction.setAmount(amount);
        }

        if (reference != null) {
            transaction.setReference(reference);
        }

        return transactionRepository.save(transaction);
    }
}
