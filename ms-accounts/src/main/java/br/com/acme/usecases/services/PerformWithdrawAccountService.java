package br.com.acme.usecases.services;

import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.IPerformDepositAccountService;
import br.com.acme.usecases.IPerformWithdrawAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawAccountService implements IPerformWithdrawAccountService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;

    @Override
    public String performWithdraw(String accountNumber, BigDecimal amount) {
        var account = this.accountRepositoryPostgres.findAccountEntityByAccountNumber(accountNumber);
        var newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        this.accountRepositoryPostgres.save(account);
        return "Withdraw successfully";
    }
}
