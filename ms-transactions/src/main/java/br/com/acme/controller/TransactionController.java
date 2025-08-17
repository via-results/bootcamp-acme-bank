package br.com.acme.controller;

import br.com.acme.controller.api.TransactionResource;
import br.com.acme.controller.request.TransactionRequest;
import br.com.acme.controller.response.TransactionConfirmedResponse;
import br.com.acme.domain.model.TransactionDomain;
import br.com.acme.usecases.*;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TransactionController implements TransactionResource {

    private final IPerformTransactionService performTransactionService;
    private final CloudWatchLogService cloudWatchLogService;

    public TransactionController(IPerformTransactionService performTransactionService, CloudWatchLogService cloudWatchLogService) {
        this.performTransactionService = performTransactionService;
        this.cloudWatchLogService = cloudWatchLogService;
    }

    @Override
    public TransactionConfirmedResponse performTransaction(TransactionRequest request) {
        var sendRequest = TransactionDomain.builder()
                .typeTransaction(request.getTypeTransaction())
                .amountTransaction(request.getAmountTransaction())
                .sourceAccount(request.getSourceAccount())
                .destinationAccount(request.getDestinationAccount())
                .build();
        this.cloudWatchLogService.sendLog("Controller Layer -> Perform Transaction :: " + request);
        return createTransactionResponse(this.performTransactionService.createTransaction(sendRequest));
    }

    private TransactionConfirmedResponse createTransactionResponse(TransactionDomain domain) {

        return TransactionConfirmedResponse.builder()
                .codeTransaction(domain.getCodeTransaction())
                .message("Transaction performed with success.")
                .build();
    }
}
