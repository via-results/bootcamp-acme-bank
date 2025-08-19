package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.IFindClientByEmailService;
import br.com.acme.usecases.IFindClientByIdService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.ClientDomain.createClientDomain;

@Service
@AllArgsConstructor
public class FindClientByEmailService implements IFindClientByEmailService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDomain find(String email) {
        var client = this.clientRepository.findByEmail(email).stream().findFirst().get();
        return ClientDomain.createClientDomain(client);
    }
}
