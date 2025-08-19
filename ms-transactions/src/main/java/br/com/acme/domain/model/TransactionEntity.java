package br.com.acme.domain.model;

import br.com.acme.domain.StatusTransaction;
import br.com.acme.domain.TypeTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_account")
    private String sourceAccount;

    @Column(name = "source_email")
    private String emailSourceAccount;

    @Column(name = "destination_account")
    private String destinationAccount;

    @Column(name = "amount_transaction")
    private BigDecimal amountTransaction;

    @Column(name = "type_transaction")
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    @Column(name = "status_transaction")
    @Enumerated(EnumType.STRING)
    private StatusTransaction statusTransaction;

    @Column(name = "code_transaction")
    private UUID codeTransaction;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
