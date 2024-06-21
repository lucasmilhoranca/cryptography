package tech.run.crypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.run.crypto.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
