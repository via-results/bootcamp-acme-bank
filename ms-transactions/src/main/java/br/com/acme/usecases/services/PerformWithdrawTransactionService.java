package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformDepositTransactionService;
import br.com.acme.usecases.IPerformWithdrawTransactionService;
import br.com.acme.usecases.external.sync.PerformDepositAccountClient;
import br.com.acme.usecases.external.sync.PerformWithdrawAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawTransactionService implements IPerformWithdrawTransactionService {

    private final PerformWithdrawAccountClient performWithdrawAccountClient;

    @Override
    public Boolean performWithdraw(String accountNumber, BigDecimal amount) {
        try{
            this.performWithdrawAccountClient.performWithdraw(accountNumber, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
