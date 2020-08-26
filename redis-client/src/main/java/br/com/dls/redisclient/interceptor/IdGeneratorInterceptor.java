package br.com.dls.redisclient.interceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class IdGeneratorInterceptor {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Before("execution(* org.springframework.data.keyvalue.core.KeyValueOperations+.insert(..)) && args(object) ")
	public void before(JoinPoint joinPoint, Object object) throws Exception {
		log.info("before insert - generating IDs");
		long startTime = System.currentTimeMillis();
		List<Object> objects = extractNestedEntitiesfromObjectToList(object, new ArrayList<>());
		for (Object obj : objects) {
			Class<?> myClass = obj.getClass();
			Field[] fields = myClass.getDeclaredFields();
			for (Field field : fields) {
				if (isId(field)) {
					log.info("Found @Id in class {} attribute {}", myClass.getName(), field.getName());
					field.setAccessible(true);
					field.set(obj, generateId(myClass.getName()));
					break;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		log.info("Total execution time: {} ms", endTime - startTime);
	}

	private Long generateId(String className) {
		Long id = redisTemplate.opsForValue().increment(className + "_sequence");
		return id;
	}

	@SuppressWarnings("unchecked")
	private List<Object> extractNestedEntitiesfromObjectToList(Object object, List<Object> entities) {
		if (isCollection(object)) {
			List<Object> list = (List<Object>) object;
			list.forEach(obj -> extractNestedEntitiesfromObjectToList(obj, entities));
			return entities;
		}
		Field fields[] = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			if (isReference(field)) {
				continue;
			}
			try {
				field.setAccessible(true);
				Object nestedObject = field.get(object);
				if (nestedObject != null && !isJavaNativeObject(nestedObject)) {
					if (isCollection(object)) {
						List<Object> nestedObjects = (List<Object>) field.get(object);
						nestedObjects.forEach(obj -> extractNestedEntitiesfromObjectToList(obj, entities));
					} else {
						extractNestedEntitiesfromObjectToList(nestedObject, entities);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		entities.add(object);

		return entities;
	}

	private boolean isJavaNativeObject(Object object) {
		return object instanceof String || object instanceof Number || object instanceof Enum;
	}

	private boolean isReference(Field field) {
		return field.isAnnotationPresent(Reference.class);
	}

	private boolean isCollection(Object object) {
		return object instanceof Collection;
	}

	private boolean isId(Field field) {
		return field.isAnnotationPresent(Id.class);
	}

}
