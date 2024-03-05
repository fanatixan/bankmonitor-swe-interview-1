package bankmonitor.controller;

import bankmonitor.controller.request.TransactionRequest;
import bankmonitor.controller.response.TransactionResponse;
import bankmonitor.controller.response.mapper.TransactionResponseMapper;
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
    TransactionResponseMapper mapper;

    @PutMapping
    TransactionResponse updateTransaction(@PathVariable long id, @RequestBody TransactionRequest update) {
        return mapper.toResponse(
                updateTransactionService.updateTransaction(
                        id,
                        update.getAmount(),
                        update.getReference()
                )
        );
    }

}
