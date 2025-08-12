package br.com.acme.usecases;

import br.com.acme.domain.ClientDomain;

import java.util.List;

public interface IListAllClientsService {

    List<ClientDomain> listAll();
}
