package br.com.acme.repository.dynamo;

import br.com.acme.domain.model.Account;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.math.BigDecimal;

@Repository
public class AccountRepository implements AccountRepositoryDynamoDB {

    private final DynamoDbTemplate dynamoDbTemplate;


    public AccountRepository(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public Account create(Account account) {
        account.setId(System.currentTimeMillis());
        return this.dynamoDbTemplate.save(account);
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        var key = Key.builder().partitionValue(accountNumber).build();
        var condition = QueryConditional.keyEqualTo(key);

        var query = QueryEnhancedRequest.builder()
                .queryConditional(condition).build();

        return this.dynamoDbTemplate.query(query, Account.class)
                .stream().flatMap(it -> it.items().stream())
                .findFirst()
                .map(Account::getBalance).get();

    }
}