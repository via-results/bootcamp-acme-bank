package br.com.acme.repository.dynamo;

import br.com.acme.domain.model.Account;

import java.math.BigDecimal;

public interface AccountRepositoryDynamoDB {

    Account create(Account account);
    BigDecimal checkBalance(String accountNumber);
}
