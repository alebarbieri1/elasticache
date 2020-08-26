package br.com.dls.redisclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dls.redisclient.domain.Account;
import br.com.dls.redisclient.domain.Customer;
import br.com.dls.redisclient.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@PostMapping
	public Customer create(@RequestBody Customer customer) {
		return customerService.create(customer);
	}

	@PutMapping
	public Customer update(@RequestBody Customer customer) {
		return customerService.update(customer);
	}

	@GetMapping("/incr")
	public void test() {
		incr();
	}

	@Async
	private void incr() {
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread() {
				public void run() {
					for (int i = 0; i < 1000; i++) {
						redisTemplate.opsForValue().increment("abc");
					}
				};
			};
			thread.start();
		}
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") String id, @RequestParam String name, @RequestParam String country) {
		return customerService.find(id, name, country);
	}

	@GetMapping("/account/{accountNumber}")
	public Customer findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
		return customerService.findByAccountNumber(accountNumber);
	}

	@GetMapping("/v2/account/{accountNumber}")
	public Account findAccount(@PathVariable("accountNumber") String accountNumber) {
		return customerService.findAccount(accountNumber);
	}

}