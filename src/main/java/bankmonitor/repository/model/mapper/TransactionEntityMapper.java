package bankmonitor.repository.model.mapper;

import bankmonitor.model.Transaction;
import bankmonitor.repository.model.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionEntityMapper {

    default Transaction toDomain(TransactionEntity entity) {
        return Transaction.of(
                entity.getId(),
                entity.getAmount(),
                entity.getReference()
        );
    }

    List<Transaction> toDomain(List<TransactionEntity> entities);

    default TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity result = new TransactionEntity();
        result.setId(transaction.getId());
        result.setAmount(transaction.getAmount());
        result.setReference(transaction.getReference());
        return result;
    }

}
