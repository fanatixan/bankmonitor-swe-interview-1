package bankmonitor.controller;

import bankmonitor.controller.request.TransactionRequest;
import bankmonitor.controller.response.TransactionResponse;
import bankmonitor.controller.response.mapper.TransactionResponseMapper;
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
    TransactionResponseMapper mapper;

    @PostMapping("/transactions")
    TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        return mapper.toResponse(
                service.createTransaction(
                        request.getAmount(),
                        request.getReference()
                )
        );
    }

}
