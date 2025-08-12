package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.IListAllClientsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.acme.domain.ClientDomain.createClientDomain;

@Service
@AllArgsConstructor
public class ListAllClientsService implements IListAllClientsService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientDomain> listAll() {
        var response = clientRepository.list();
        return  response.stream().map(ClientDomain::createClientDomain).toList();
    }
}
