package br.com.acme.usecases;

import br.com.acme.domain.ClientDomain;

public interface IFindClientByIdService {

    ClientDomain find(Long id);
}
