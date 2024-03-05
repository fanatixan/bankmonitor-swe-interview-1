package bankmonitor.controller;

import bankmonitor.controller.request.TransactionRequest;
import bankmonitor.model.Transaction;
import bankmonitor.service.CreateTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class CreateTransactionController {

    CreateTransactionService service;

    @PostMapping("/transactions")
    Transaction createTransaction(@RequestBody TransactionRequest request) {
        return service.createTransaction(request.getAmount(), request.getReference());
    }

}