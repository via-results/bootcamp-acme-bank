package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.IFindClientByDocumentService;
import br.com.acme.usecases.IFindClientByEmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindClientByDocumentService implements IFindClientByDocumentService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDomain find(String document) {
        var client = this.clientRepository.findByDocument(document).stream().findAny().get();
        return ClientDomain.createClientDomain(client);
    }
}
