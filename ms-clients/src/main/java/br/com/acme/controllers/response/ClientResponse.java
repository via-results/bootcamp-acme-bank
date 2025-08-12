package br.com.acme.controllers.response;

import br.com.acme.domain.StatusClient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponse {

    private  Long id;
    private String name;
    private String email;
    private String document;
    private BigDecimal income;
    private String createdAt;
    private StatusClient status;
}
