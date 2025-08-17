package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "PerformTransferAccountClient", url = "${client.url}")
public interface PerformTransferAccountClient {

    @GetMapping("/perform-transfer")
    void performTransfer(@RequestParam("accountSource") String accountSource,
                           @RequestParam("accountDestination") String accountDestination,
                           @RequestParam("amount") BigDecimal amount);
}
