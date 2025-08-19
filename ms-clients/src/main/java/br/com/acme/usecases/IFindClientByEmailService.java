package br.com.acme.usecases;

import br.com.acme.domain.ClientDomain;

public interface IFindClientByEmailService {

    ClientDomain find(String email);
}
