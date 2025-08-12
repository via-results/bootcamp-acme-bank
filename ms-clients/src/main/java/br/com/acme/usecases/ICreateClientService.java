package br.com.acme.usecases;

import br.com.acme.domain.ClientDomain;

public interface ICreateClientService {

    ClientDomain execute(ClientDomain clientDomain);
}
