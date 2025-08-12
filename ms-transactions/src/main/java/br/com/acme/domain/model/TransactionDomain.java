package br.com.acme.domain.model;

import br.com.acme.domain.StatusTransaction;
import br.com.acme.domain.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDomain {

    private Long id;
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amountTransaction;
    private TypeTransaction typeTransaction;
    private StatusTransaction statusTransaction;
    private UUID codeTransaction;
    private LocalDateTime createdAt;

    public boolean hasNoBalance(BigDecimal balance) {
        return balance.compareTo(BigDecimal.ZERO) <= 0;
    }

    public static TransactionDomain fromEntity(TransactionEntity entity) {
        return TransactionDomain.builder()
                .id(entity.getId())
                .amountTransaction(entity.getAmountTransaction())
                .sourceAccount(entity.getSourceAccount())
                .destinationAccount(entity.getDestinationAccount())
                .typeTransaction(entity.getTypeTransaction())
                .codeTransaction(entity.getCodeTransaction())
                .statusTransaction(entity.getStatusTransaction())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static TransactionEntity toEntityPending(TransactionDomain transactionDomain) {
        return TransactionEntity.builder()
                .sourceAccount(transactionDomain.sourceAccount)
                .destinationAccount(transactionDomain.destinationAccount)
                .amountTransaction(transactionDomain.amountTransaction)
                .typeTransaction(transactionDomain.typeTransaction)
                .createdAt(LocalDateTime.now())
                .statusTransaction(StatusTransaction.PENDING)
                .codeTransaction(UUID.randomUUID())
                .build();
    }

    public static TransactionEntity toEntityCompleted(TransactionDomain transactionDomain) {
        return TransactionEntity.builder()
                .sourceAccount(transactionDomain.sourceAccount)
                .destinationAccount(transactionDomain.destinationAccount)
                .amountTransaction(transactionDomain.amountTransaction)
                .typeTransaction(transactionDomain.typeTransaction)
                .statusTransaction(StatusTransaction.COMPLETED)
                .codeTransaction(transactionDomain.codeTransaction)
                .createdAt(transactionDomain.createdAt)
                .build();
    }
}
