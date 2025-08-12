package br.com.acme.domain;

import br.com.acme.domain.model.Client;
import br.com.acme.repository.ClientRepository;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClientDomain {

    private Long id;
    private String name;
    private String email;
    private String document;
    private BigDecimal income;
    private LocalDateTime createdAt;
    private StatusClient status;

    public static Client createClient(ClientDomain clientDomain) {
        return Client.builder()
                .document(clientDomain.document)
                .email(clientDomain.email)
                .name(clientDomain.name)
                .income(clientDomain.income)
                .build();
    }

    public static ClientDomain createClientDomain(Client client) {
        return ClientDomain.builder()
                .id(client.getId())
                .document(client.getDocument())
                .email(client.getEmail())
                .name(client.getName())
                .income(client.getIncome())
                .status(client.getStatus())
                .createdAt(client.getCreatedAt())
                .build();
    }
}
