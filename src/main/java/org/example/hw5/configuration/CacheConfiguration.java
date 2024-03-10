package org.example.hw5.configuration;

import org.example.hw5.configuration.enums.Cache;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties properties) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(properties.getHost());
        configuration.setPort(properties.getPort());
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory) {
        var defaultConf = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        Stream.of(Cache.values()).forEach(cache -> {
            redisCacheConfigurationMap.put(
                    cache.getVal(),
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(cache.getExpire()));
        });
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConf)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }


}
