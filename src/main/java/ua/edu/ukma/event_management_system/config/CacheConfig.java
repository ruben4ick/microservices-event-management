package ua.edu.ukma.event_management_system.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.ukma.event_management_system.cache.CustomCacheManager;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CustomCacheManager manager = new CustomCacheManager();
        manager.putCache(new ConcurrentMapCache("buildings"));
        manager.putCache(new ConcurrentMapCache("building"));
        return new CustomCacheManager();
    }
}
