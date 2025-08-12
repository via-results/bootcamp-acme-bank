package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.IFindClientByIdService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.ClientDomain.createClientDomain;

@Service
@AllArgsConstructor
public class FindClientByIdService implements IFindClientByIdService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDomain find(Long id) {
        return createClientDomain(clientRepository.getClient(id));
    }
}
