package br.com.acme.usecases.services;

import br.com.acme.domain.AccountDomain;
import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.IGetAccountByClientDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.AccountDomain.createAccountDomain;

@Service
@AllArgsConstructor
public class GetAccountByClientDocumentService implements IGetAccountByClientDocumentService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;

    @Override
    public AccountDomain getAccountByClientDocument(String document) {
        var entity =  accountRepositoryPostgres.findAccountEntityByClientDocument(document);
        return createAccountDomain(entity);
    }
}
