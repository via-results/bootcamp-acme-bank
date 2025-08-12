package br.com.acme.domain.model;

import br.com.acme.domain.AccountStatus;
import br.com.acme.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Account {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private Long client;
    private String clientDocument;
    private LocalDateTime createdAt;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("account_number")
    @DynamoDbSecondaryPartitionKey(indexNames = "accountNumber-index")
    public String getAccountNumber() {
        return accountNumber;
    }

    @DynamoDbAttribute("account_type")
    public AccountType getAccountType() {
        return accountType;
    }

    @DynamoDbAttribute("account_status")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    @DynamoDbAttribute("account_balance")
    public BigDecimal getBalance() {
        return balance;
    }

    @DynamoDbAttribute("client_id")
    public Long getClient() {
        return client;
    }

    @DynamoDbAttribute("client_document")
    public String getClientDocument() {
        return clientDocument;
    }

}
