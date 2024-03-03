package bankmonitor.controller;

import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.json.JSONObject;
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
    Transaction createTransaction(@RequestBody String jsonData) {
        Transaction data = new Transaction(jsonData);
        return transactionRepository.save(data);
    }

    @PutMapping("/transactions/{id}")
    ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody String update) {

        JSONObject updateJson = new JSONObject(update);

        Optional<Transaction> data = transactionRepository.findById(id);
        if (!data.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Transaction transaction = data.get();
        JSONObject trdata = new JSONObject(transaction.getData());

        if (updateJson.has(Transaction.AMOUNT_KEY)) {
            trdata.put(Transaction.AMOUNT_KEY, updateJson.getInt(Transaction.AMOUNT_KEY));
        }

        if (updateJson.has(Transaction.REFERENCE_KEY)) {
            trdata.put(Transaction.REFERENCE_KEY, updateJson.getString(Transaction.REFERENCE_KEY));
        }
        transaction.setData(trdata.toString());

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return ResponseEntity.ok(updatedTransaction);
    }
}
