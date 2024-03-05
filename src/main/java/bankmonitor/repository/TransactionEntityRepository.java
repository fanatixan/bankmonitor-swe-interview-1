package bankmonitor.repository;

import bankmonitor.repository.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {
}
