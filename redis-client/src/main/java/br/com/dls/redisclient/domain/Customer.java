package br.com.dls.redisclient.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("customer")
public class Customer {

	@Id
	private Long id;
	@Indexed
	private String name;
	@Reference
	private List<Account> accounts;
	private Address address;

}
