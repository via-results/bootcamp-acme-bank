package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performWithdrawAccountClient", url = "${client.url}")
public interface PerformWithdrawAccountClient {

    @GetMapping("/perform-withdraw")
    void performWithdraw(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount);
}
