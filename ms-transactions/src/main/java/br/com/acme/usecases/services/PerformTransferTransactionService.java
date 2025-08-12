package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformTransferTransactionService;
import br.com.acme.usecases.IPerformWithdrawTransactionService;
import br.com.acme.usecases.external.sync.PerformTransferAccountClient;
import br.com.acme.usecases.external.sync.PerformWithdrawAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferTransactionService implements IPerformTransferTransactionService {

    private final PerformTransferAccountClient performTransferAccountClient;

    @Override
    public Boolean performTransfer (String accountSource, String accountDestination, BigDecimal amount) {
        try{
            this.performTransferAccountClient.performTransfer(accountSource, accountDestination, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
