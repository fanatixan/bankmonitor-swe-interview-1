package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class TransactionController {

    TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping("/transactions")
    Transaction createTransaction(@RequestBody TransactionRequest request) {
        return transactionRepository.save(request.toTransaction());
    }

    @PutMapping("/transactions/{id}")
    ResponseEntity<Transaction> updateTransaction(@PathVariable long id, @RequestBody TransactionRequest update) {
        Optional<Transaction> data = transactionRepository.findById(id);
        if (data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Transaction transaction = data.get();

        if (update.getAmount() != null) {
            transaction.setAmount(update.getAmount());
        }

        if (update.getReference() != null) {
            transaction.setReference(update.getReference());
        }

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static final class TransactionRequest {
        Integer amount;
        String reference;

        public Transaction toTransaction() {
            return Transaction.of(amount, reference);
        }
    }

}
