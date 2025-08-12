package br.com.acme.usecases.services;

import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.IPerformTransferAccountService;
import br.com.acme.usecases.IPerformWithdrawAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferAccountService implements IPerformTransferAccountService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;

    @Override
    public String performTransfer(String accountSource, String accountDestination, BigDecimal amount) {
        // Debit account source
        var accountSourceDebit = this.accountRepositoryPostgres.findAccountEntityByAccountNumber(accountSource);
        var newBalanceSource = accountSourceDebit.getBalance().subtract(amount);
        accountSourceDebit.setBalance(newBalanceSource);
        this.accountRepositoryPostgres.save(accountSourceDebit);

        // Deposit account destination
        var accountDestinationCredit = this.accountRepositoryPostgres.findAccountEntityByAccountNumber(accountDestination);
        var newBalanceDestination = accountDestinationCredit.getBalance().add(amount);
        accountDestinationCredit.setBalance(newBalanceDestination);
        this.accountRepositoryPostgres.save(accountDestinationCredit);

        return "Transfer successfully";
    }
}
