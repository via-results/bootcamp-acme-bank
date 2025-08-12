package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformDepositTransactionService {

    Boolean performDeposit(String accountNumber, BigDecimal amount);
}
