package br.com.acme.domain;

import br.com.acme.domain.model.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDomain {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Long client;
    private String clientDocument;
    private LocalDateTime createdAt;

    public static AccountEntity createAccountEntity(AccountDomain accountDomain) {
        return AccountEntity.builder()
                .accountNumber(accountDomain.accountNumber)
                .accountType(accountDomain.accountType)
                .accountStatus(accountDomain.accountStatus)
                .balance(accountDomain.balance)
                .client(accountDomain.client)
                .clientDocument(accountDomain.clientDocument)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AccountDomain createAccountDomain(AccountEntity account){
        return AccountDomain.builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .clientDocument(account.getClientDocument())
                .client(account.getClient())
                .createdAt(account.getCreatedAt())
                .accountStatus(account.getAccountStatus())
                .build();
    }
}
