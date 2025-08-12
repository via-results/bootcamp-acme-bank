package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.ICreateClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.ClientDomain.createClient;
import static br.com.acme.domain.ClientDomain.createClientDomain;

@Service
@AllArgsConstructor
public class CreateClientService  implements ICreateClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDomain execute(ClientDomain clientDomain) {
        return createClientDomain(this.clientRepository.save(createClient(clientDomain)));
    }
}
