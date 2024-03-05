package bankmonitor.controller;

import bankmonitor.controller.request.TransactionRequest;
import bankmonitor.model.Transaction;
import bankmonitor.service.UpdateTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions/{id}")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UpdateTransactionController {

    UpdateTransactionService updateTransactionService;

    @PutMapping
    Transaction updateTransaction(@PathVariable long id, @RequestBody TransactionRequest update) {
        return updateTransactionService.updateTransaction(id, update.getAmount(), update.getReference());
    }

}
