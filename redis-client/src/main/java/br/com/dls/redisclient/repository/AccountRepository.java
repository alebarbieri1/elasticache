package br.com.dls.redisclient.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.dls.redisclient.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	Account findByNumber(String number);
}
