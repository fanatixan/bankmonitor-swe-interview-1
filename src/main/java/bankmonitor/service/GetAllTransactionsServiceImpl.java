package bankmonitor.service;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
class GetAllTransactionsServiceImpl implements GetAllTransactionsService {

    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        log.info("Returning all transactions");
        var result = transactionRepository.findAll();
        log.debug("Result count: {}", result.size());
        return result;
    }

}
