package br.com.dls.redisclient.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import br.com.dls.redisclient.annotation.RedisMainHash;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class InsertInterceptor {
	@After("execution(* org.springframework.data.keyvalue.core.KeyValueOperations+.insert(..)) && args(object) ")
	public void after(JoinPoint joinPoint, Object object) throws Exception {
		Class<?> myClass = object.getClass();
		if (isInterceptable(myClass)) {
			log.info("after insert - sending message to SQS");
		} else {
			log.info("after insert");
		}
	}

	private boolean isInterceptable(Class<?> myClass) {
		return myClass.isAnnotationPresent(RedisMainHash.class);
	}
}
