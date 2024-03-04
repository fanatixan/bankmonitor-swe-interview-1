package bankmonitor.controller;

import bankmonitor.controller.request.TransactionRequest;
import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transactions/{id}")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UpdateTransactionController {

    TransactionRepository transactionRepository;

    @PutMapping
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

}
