package br.com.acme.repository;

import br.com.acme.domain.StatusClient;
import br.com.acme.domain.model.Client;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ClientRepository {

    private final DynamoDbTemplate dynamoDbTemplate;

    public ClientRepository(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public Client save(Client client) {
        client.setId(System.currentTimeMillis());
        client.setCreatedAt(LocalDateTime.now());
        client.setStatus(StatusClient.ACTIVE);
        return this.dynamoDbTemplate.save(client);
    }

    public List<Client> list() {
      return  this.dynamoDbTemplate.scanAll(Client.class)
              .items().stream()
              .filter(it -> !it.getStatus().equals(StatusClient.INACTIVE))
              .toList();
    }

    public Client getClient(Long id) {
        return this.dynamoDbTemplate
                .load(Key.builder().partitionValue(id)
                        .build(), Client.class);
    }

    public void deleteClient(Long id) {
        var response = this.dynamoDbTemplate
                .load(Key.builder().partitionValue(id)
                        .build(), Client.class);
        Objects.requireNonNull(response).setStatus(StatusClient.INACTIVE);
        this.dynamoDbTemplate.save(response);
    }
}
