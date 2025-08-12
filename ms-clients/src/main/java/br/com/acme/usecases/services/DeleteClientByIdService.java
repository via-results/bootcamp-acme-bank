package br.com.acme.usecases.services;

import br.com.acme.domain.ClientDomain;
import br.com.acme.repository.ClientRepository;
import br.com.acme.usecases.IDeleteClientByIdService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteClientByIdService implements IDeleteClientByIdService {

    private final ClientRepository clientRepository;

    @Override
    public String delete(Long id) {
            clientRepository.deleteClient(id);
            return "Client was deleted";
        }
}
