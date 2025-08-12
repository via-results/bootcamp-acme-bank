package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformDepositAccountService {
    String performDeposit(String accountNumber, BigDecimal amount);
}
