package br.com.dls.redisclient.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class UpdateInterceptor {
	@After("execution(* org.springframework.data.keyvalue.core.KeyValueOperations+.update(..)) && args(id, object) ")
	public void after(JoinPoint joinPoint, Object id, Object object) throws Exception {
		log.info("after update - sending message to SQS");
	}
}
