package bankmonitor.controller.response.mapper;

import bankmonitor.controller.response.TransactionResponse;
import bankmonitor.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    TransactionResponse toResponse(Transaction transaction);

    List<TransactionResponse> toResponse(List<Transaction> transactions);

}
