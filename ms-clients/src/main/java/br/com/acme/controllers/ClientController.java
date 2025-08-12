package br.com.acme.controllers;

import br.com.acme.controllers.api.ClientsResources;
import br.com.acme.controllers.requests.ClientRequest;
import br.com.acme.controllers.response.ClientResponse;
import br.com.acme.domain.ClientDomain;
import br.com.acme.usecases.ICreateClientService;
import br.com.acme.usecases.IDeleteClientByIdService;
import br.com.acme.usecases.IFindClientByIdService;
import br.com.acme.usecases.IListAllClientsService;
import br.com.acme.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController implements ClientsResources {

    private final Utils utils;

    private final ICreateClientService createClientService;
    private final IListAllClientsService listAllClientsService;
    private final IFindClientByIdService findClientByIdService;
    private final IDeleteClientByIdService deleteClientByIdService;

    @Override
    public ClientResponse create(ClientRequest request) {
        return createResponse(this.createClientService.execute(createDomain(request)));
    }

    @Override
    public List<ClientResponse> list() {
        return listAllClientsService.listAll().stream().map(this::createResponse).toList();
    }

    @Override
    public ClientResponse getClient(Long id) {
        return createResponse(this.findClientByIdService.find(id));
    }

    @Override
    public String deleteClient(Long id) {
        return deleteClientByIdService.delete(id);
    }

    private ClientDomain createDomain(ClientRequest request) {
        return ClientDomain.builder()
                .name(request.getName())
                .email(request.getEmail())
                .document(request.getDocument())
                .income(request.getIncome())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private ClientResponse createResponse(ClientDomain domain) {
        return ClientResponse.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .document(domain.getDocument())
                .income(domain.getIncome())
                .createdAt(utils.formatDate(domain.getCreatedAt()))
                .status(domain.getStatus())
                .build();
    }

}
