package bankmonitor.repository;

import bankmonitor.repository.model.TransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {
}
