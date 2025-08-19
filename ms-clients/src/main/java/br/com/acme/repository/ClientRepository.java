package br.com.acme.repository;

import br.com.acme.domain.StatusClient;
import br.com.acme.domain.model.Client;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ClientRepository {

    private final DynamoDbTable<Client> clientTable;

    public ClientRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    public Client save(Client client) {
        client.setId(System.currentTimeMillis());
        client.setCreatedAt(LocalDateTime.now());
        client.setStatus(StatusClient.ACTIVE);
        clientTable.putItem(client);
        return client;
    }

    public List<Client> list() {
        return clientTable.scan().items().stream()
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .collect(Collectors.toList());
    }

    public Client getClient(Long id) {
        return clientTable.getItem(Key.builder().partitionValue(id).build());
    }

    public void deleteClient(Long id) {
        Client client = clientTable.getItem(Key.builder().partitionValue(id).build());
        Objects.requireNonNull(client).setStatus(StatusClient.INACTIVE);
        clientTable.putItem(client);
    }

    public List<Client> findByEmail(String email) {
        QueryConditional condition = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(email).build()
        );

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        return clientTable.index("email-index")
                .query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .toList();
    }

    public List<Client> findByDocument(String document) {
        QueryConditional condition = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(document).build()
        );

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        return clientTable.index("document-index")
                .query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .toList();
    }
}