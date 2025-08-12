package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformWithdrawAccountService {
    String performWithdraw(String accountNumber, BigDecimal amount);
}
