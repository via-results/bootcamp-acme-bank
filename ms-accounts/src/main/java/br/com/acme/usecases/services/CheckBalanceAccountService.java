package br.com.acme.usecases.services;

import br.com.acme.domain.AccountDomain;
import br.com.acme.repository.dynamo.AccountRepositoryDynamoDB;
import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.ICheckBalanceAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckBalanceAccountService implements ICheckBalanceAccountService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;

    @Override
    public BigDecimal getBalance(String accountNumber) {
       return this.accountRepositoryPostgres.findAccountEntityByAccountNumber(accountNumber).getBalance();
    }
}
