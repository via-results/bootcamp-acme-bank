package br.com.acme.usecases.services;

import br.com.acme.domain.AccountDomain;
import br.com.acme.repository.postgres.AccountRepositoryPostgres;
import br.com.acme.usecases.ICreateAccountService;
import br.com.acme.usecases.external.sync.GetClientToAccount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.AccountDomain.createAccountDomain;
import static br.com.acme.domain.AccountDomain.createAccountEntity;

@Service
@AllArgsConstructor
public class CreateAccountService implements ICreateAccountService {

    private final AccountRepositoryPostgres accountRepositoryPostgres;
    private final GetClientToAccount clientToAccount;

    @Override
    public AccountDomain createAccount(AccountDomain accountDomain) {
        var client = clientToAccount.clientToAccount(accountDomain.getClient());
        accountDomain.setClient(client.getId());
        accountDomain.setClientDocument(client.getDocument());
        var entity = createAccountEntity(accountDomain);
        return createAccountDomain(accountRepositoryPostgres.save(entity));
    }
}
