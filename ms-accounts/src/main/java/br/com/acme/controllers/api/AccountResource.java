package br.com.acme.controllers.api;

import br.com.acme.controllers.request.AccountRequest;
import br.com.acme.controllers.response.AccountResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1/accounts")
public interface AccountResource {

    @PostMapping
    AccountResponse create(@RequestBody AccountRequest request);

    @GetMapping("/check-balance")
    BigDecimal checkBalanceAccount(@RequestParam("accountNumber") String accountNumber);

    @GetMapping("/client-document")
    AccountResponse verifyAccountClientDocument(@RequestParam("clientDocument") String clientDocument);

    @GetMapping("/perform-deposit")
    String performDeposit(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount);

    @GetMapping("/perform-withdraw")
    String performWithdraw(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") BigDecimal amount);

    @GetMapping("/perform-transfer")
    String performTransfer(@RequestParam("accountSource") String accountSource,
                           @RequestParam("accountDestination") String accountDestination,
                           @RequestParam("amount") BigDecimal amount);
}
