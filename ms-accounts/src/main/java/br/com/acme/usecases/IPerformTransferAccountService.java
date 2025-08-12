package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformTransferAccountService {

    String performTransfer(String accountSource, String accountDestination, BigDecimal amount);
}
