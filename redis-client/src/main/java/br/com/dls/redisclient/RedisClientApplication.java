package br.com.dls.redisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class) // avoid an exception when running at local
public class RedisClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisClientApplication.class, args);
	}

}
