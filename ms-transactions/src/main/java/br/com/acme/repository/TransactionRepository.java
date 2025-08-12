package br.com.acme.repository;

import br.com.acme.domain.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository <TransactionEntity, Long> {

    TransactionEntity findTransactionByCodeTransaction(UUID codeTransaction);
}
