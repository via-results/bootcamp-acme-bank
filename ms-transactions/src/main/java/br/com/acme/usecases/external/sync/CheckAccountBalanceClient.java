package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "checkAccountBalanceClient", url = "${client.url}")
public interface CheckAccountBalanceClient {

    @GetMapping(value = "/check-balance", consumes = "application/json", produces = "application/json")
    BigDecimal checkAccountBalance(@RequestParam("accountNumber") String accountNumber);
}
