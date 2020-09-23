package br.com.dls.redisclient.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash(value = "account", timeToLive = 10000)
public class Account {
	@Id
	private Long id;
	@Indexed
	private String number;
	private int balance;
}
