package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class GetAllTransactionsController {

    TransactionRepository transactionRepository;

    @GetMapping
    List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
