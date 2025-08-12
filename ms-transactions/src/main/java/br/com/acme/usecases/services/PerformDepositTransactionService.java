package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformDepositTransactionService;
import br.com.acme.usecases.external.sync.PerformDepositAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositTransactionService implements IPerformDepositTransactionService {

    private final PerformDepositAccountClient performDepositAccountClient;

    @Override
    public Boolean performDeposit(String accountNumber, BigDecimal amount) {
        try{
            this.performDepositAccountClient.performDeposit(accountNumber, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
