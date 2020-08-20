package br.com.dls.redisclient.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
public class Address {

	@Id
	private Long id;
	private String city;
	@Indexed
	private String country;
	private String zipcode;
}
