package br.com.dls.redisclient.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.dls.redisclient.domain.Account;
import br.com.dls.redisclient.domain.Customer;
import br.com.dls.redisclient.repository.AccountRepository;
import br.com.dls.redisclient.repository.CustomerRepository;
import br.com.dls.redisclient.utils.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final AccountRepository accountRepository;

	public Customer create(Customer customer) {
		accountRepository.saveAll(customer.getAccounts());
		return (Customer) customerRepository.save(customer);
	}

	public Customer update(Customer customer) {
		return (Customer) customerRepository.save(customer);
	}

	public Customer find(String id) {
		Optional<Customer> optCustomer = customerRepository.findById(id);
		if (optCustomer.isPresent()) {
			return optCustomer.get();
		}
		throw new CustomerNotFoundException(id);
	}

	public List<Customer> findByName(String name) {
		List<Customer> customers = customerRepository.findByName(name);
		return customers;
	}

	public Customer findByAccountNumber(String accountNumber) {
		return customerRepository.findByAccountsNumber(accountNumber);
	}

	public Account findAccount(String accountNumber) {
		return accountRepository.findByNumber(accountNumber);
	}

}
