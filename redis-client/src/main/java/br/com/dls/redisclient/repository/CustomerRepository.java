package br.com.dls.redisclient.repository;

import br.com.dls.redisclient.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

	Optional<Customer> findByNameAndAddressCountry(String name, String country);

	Optional<Customer> findByAddressCountry(String country);

	Customer findByAccountsNumber(String accountNumber);
}
