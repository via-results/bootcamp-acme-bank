package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performDepositAccountClient", url = "${client.url}")
public interface PerformDepositAccountClient {
    @GetMapping("/perform-deposit")
    void performDeposit(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount);
}
