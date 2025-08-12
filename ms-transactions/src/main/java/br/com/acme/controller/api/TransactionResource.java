package br.com.acme.controller.api;

import br.com.acme.controller.request.TransactionRequest;
import br.com.acme.controller.response.TransactionConfirmedResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/transactions")
public interface TransactionResource {

    @PostMapping
    TransactionConfirmedResponse performTransaction(@RequestBody TransactionRequest request);
}
