package br.com.acme.controllers.request;

import br.com.acme.domain.AccountStatus;
import br.com.acme.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {

    private String accountNumber;
    private AccountType accountType;
    private Long client;
}
