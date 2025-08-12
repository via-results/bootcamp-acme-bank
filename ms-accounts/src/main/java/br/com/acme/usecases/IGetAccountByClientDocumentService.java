package br.com.acme.usecases;

import br.com.acme.domain.AccountDomain;

public interface IGetAccountByClientDocumentService {

    AccountDomain getAccountByClientDocument(String document);
}
