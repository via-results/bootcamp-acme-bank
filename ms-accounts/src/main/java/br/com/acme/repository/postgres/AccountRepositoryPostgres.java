package br.com.acme.repository.postgres;


import br.com.acme.domain.AccountDomain;
import br.com.acme.domain.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryPostgres extends JpaRepository<AccountEntity, Long> {

    AccountEntity findAccountEntityByAccountNumber(String accountNumber);
    AccountEntity findAccountEntityByClientDocument(String clientDocument);
}
