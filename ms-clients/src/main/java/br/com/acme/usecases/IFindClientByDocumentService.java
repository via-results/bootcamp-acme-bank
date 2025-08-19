package br.com.acme.usecases;

import br.com.acme.domain.ClientDomain;

public interface IFindClientByDocumentService {

    ClientDomain find(String document);
}
