package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformTransferTransactionService {

    Boolean performTransfer(String accountSource, String accountDestination, BigDecimal amount);
}
