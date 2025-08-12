package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performDepositAccountClient", url = "http://localhost:8082/api/v1/accounts")
public interface PerformDepositAccountClient {
    @GetMapping("/perform-deposit")
    String performDeposit(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount);
}
