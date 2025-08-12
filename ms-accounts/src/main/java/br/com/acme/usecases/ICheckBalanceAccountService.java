package br.com.acme.usecases;

import java.math.BigDecimal;

public interface ICheckBalanceAccountService {

    BigDecimal getBalance(String accountNumber);
}
