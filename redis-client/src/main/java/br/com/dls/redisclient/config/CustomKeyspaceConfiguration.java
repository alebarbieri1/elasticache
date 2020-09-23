package br.com.dls.redisclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;

public class CustomKeyspaceConfiguration extends KeyspaceConfiguration {

	@Value("${spring.redis.time-to-live-in-hours:24}")
	private Long timeToLiveInHours;

	@Override
	public boolean hasSettingsFor(Class<?> type) {
		return type.isAnnotationPresent(RedisHash.class);
	}

	@Override
	public KeyspaceSettings getKeyspaceSettings(Class<?> type) {
		KeyspaceSettings keyspaceSettings = new KeyspaceSettings(type, type.getName());
		keyspaceSettings.setTimeToLive(timeToLiveInHours * 60 * 60);

		return keyspaceSettings;
	}
}