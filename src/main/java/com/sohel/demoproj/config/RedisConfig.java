package com.sohel.demoproj.config;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Configuration
@EnableCaching
public class RedisConfig implements CachingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Objects.requireNonNull(Duration.ofMinutes(10)))
                .disableCachingNullValues()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(Objects.requireNonNull(connectionFactory))
                .cacheDefaults(config)
                .build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(@NonNull RuntimeException exception, @NonNull Cache cache,
                    @NonNull Object key) {
                log.warn(
                        "Redis connection unavailable during GET for key '{}' in cache '{}'. Falling back to database.",
                        key, cache != null ? cache.getName() : "unknown");
            }

            @Override
            public void handleCachePutError(@NonNull RuntimeException exception, @NonNull Cache cache,
                    @NonNull Object key, @Nullable Object value) {
                log.warn("Redis connection unavailable during PUT for key '{}' in cache '{}'. Skipping cache write.",
                        key, cache != null ? cache.getName() : "unknown");
            }

            @Override
            public void handleCacheEvictError(@NonNull RuntimeException exception, @NonNull Cache cache,
                    @NonNull Object key) {
                log.warn(
                        "Redis connection unavailable during EVICT for key '{}' in cache '{}'. Skipping cache eviction.",
                        key, cache != null ? cache.getName() : "unknown");
            }

            @Override
            public void handleCacheClearError(@NonNull RuntimeException exception, @NonNull Cache cache) {
                log.warn("Redis connection unavailable during CLEAR for cache '{}'. Skipping cache clear.",
                        cache != null ? cache.getName() : "unknown");
            }
        };
    }
}
