package br.com.acme.controller.request;

import br.com.acme.domain.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amountTransaction;
    private TypeTransaction typeTransaction;
}
