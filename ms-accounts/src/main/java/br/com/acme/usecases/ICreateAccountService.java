package br.com.acme.usecases;

import br.com.acme.domain.AccountDomain;

public interface ICreateAccountService {

    AccountDomain createAccount(AccountDomain accountDomain);
}
