package br.com.acme.controllers;

import br.com.acme.controllers.api.AccountResource;
import br.com.acme.controllers.request.AccountRequest;
import br.com.acme.controllers.response.AccountResponse;
import br.com.acme.domain.AccountDomain;
import br.com.acme.domain.AccountStatus;
import br.com.acme.usecases.*;
import br.com.acme.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class AccountController implements AccountResource {

    private final Utils utils;
    private final ICreateAccountService createAccountService;
    private final ICheckBalanceAccountService checkBalanceAccountService;
    private final IGetAccountByClientDocumentService getAccountByClientDocumentService;
    private final IPerformDepositAccountService performDepositAccountService;
    private final IPerformWithdrawAccountService performWithdrawAccountService;
    private final IPerformTransferAccountService performTransferAccountService;

    @Override
    public AccountResponse create(AccountRequest request) {
        var response = this.createAccountService.createAccount(accountDomain(request));
        return this.accountResponse(response);
    }

    @Override
    public BigDecimal checkBalanceAccount(String accountNumber) {
        return this.checkBalanceAccountService.getBalance(accountNumber);
    }

    @Override
    public AccountResponse getAccountClientDocument(String clientDocument) {
        return accountResponse(this.getAccountByClientDocumentService.getAccountByClientDocument(clientDocument));
    }

    @Override
    public AccountResponse getAccountClientAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public String performDeposit(String accountNumber, BigDecimal amount) {
        return performDepositAccountService.performDeposit(accountNumber, amount);
    }

    @Override
    public String performWithdraw(String accountNumber, BigDecimal amount) {
        return performWithdrawAccountService.performWithdraw(accountNumber, amount);
    }

    @Override
    public String performTransfer(String accountSource, String accountDestination, BigDecimal amount) {
        return performTransferAccountService.performTransfer(accountSource, accountDestination, amount);
    }

    private AccountDomain accountDomain(AccountRequest accountRequest) {
        return AccountDomain.builder()
                .accountNumber(accountRequest.getAccountNumber())
                .accountType(accountRequest.getAccountType())
                .client(accountRequest.getClient())
                .accountStatus(AccountStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private AccountResponse accountResponse(AccountDomain accountDomain) {
        return AccountResponse.builder()
                .id(accountDomain.getId())
                .client(accountDomain.getClient())
                .accountNumber(accountDomain.getAccountNumber())
                .accountStatus(accountDomain.getAccountStatus())
                .accountType(accountDomain.getAccountType())
                .balance(accountDomain.getBalance())
                .createdAt(utils.formatDate(accountDomain.getCreatedAt()))
                .build();
    }
}
