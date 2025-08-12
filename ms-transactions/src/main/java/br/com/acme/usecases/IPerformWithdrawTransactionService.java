package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformWithdrawTransactionService {

    Boolean performWithdraw(String accountNumber, BigDecimal amount);
}
