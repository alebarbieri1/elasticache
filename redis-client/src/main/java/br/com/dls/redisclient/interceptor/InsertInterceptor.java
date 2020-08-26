package br.com.dls.redisclient.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class InsertInterceptor {
	@After("execution(* org.springframework.data.keyvalue.core.KeyValueOperations+.insert(..)) && args(object) ")
	public void after(JoinPoint joinPoint, Object object) throws Exception {
		log.info("after insert - sending message to SQS");
	}
}
