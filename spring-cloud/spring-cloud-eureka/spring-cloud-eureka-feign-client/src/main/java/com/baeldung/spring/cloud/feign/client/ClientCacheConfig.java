package com.baeldung.spring.cloud.feign.client;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
class ClientCacheConfig extends CachingConfigurerSupport{

	@Value("${cache-config.time-to-live-in-seconds}")
	private int cacheTimeToLive;
	
	@Override
	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {

            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name,
                    CacheBuilder.newBuilder().expireAfterWrite(cacheTimeToLive - 5, TimeUnit.SECONDS).maximumSize(100).build().asMap(), false);
            }
        };

        return cacheManager;
	}
}