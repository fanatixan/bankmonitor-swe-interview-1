package bankmonitor.repository;

import bankmonitor.model.Transaction;
import bankmonitor.repository.model.mapper.TransactionEntityMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class JpaTransactionRepository implements TransactionRepository {

    TransactionEntityRepository repository;
    TransactionEntityMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> findAll() {
        return mapper.toDomain(repository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Transaction> findById(long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = mapper.toEntity(transaction);
        return mapper.toDomain(repository.save(entity));
    }
}
