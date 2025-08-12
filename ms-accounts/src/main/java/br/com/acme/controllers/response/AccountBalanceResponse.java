package br.com.acme.controllers.response;


import br.com.acme.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceResponse {

    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
}
