package br.com.acme.usecases.services;

import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.IPerformDepositAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositAccountService  implements IPerformDepositAccountService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;

    @Override
    public String performDeposit(String accountNumber, BigDecimal amount) {
        var account = this.accountRepositoryPostgres.findAccountEntityByAccountNumber(accountNumber);
        if (account == null ) {
            return "Account don't exists, please confirm destination account to deposit";
        }
        var newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        this.accountRepositoryPostgres.save(account);
        return "Deposit successfully";
    }
}
