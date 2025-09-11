package ua.edu.ukma.event_management_system.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomCacheManager implements CacheManager {

    private Map<String, Cache> manager = new HashMap<>();

    @Override
    public Cache getCache(String name) {
        Cache value = manager.putIfAbsent(name, new ConcurrentMapCache(name));
        if (value == null){
            return manager.get(name);
        }
        return value;
    }

    @Override
    public Collection<String> getCacheNames() {
        return manager.keySet().stream().toList();
    }

    public void putCache(Cache cache) {
        manager.put(cache.getName(), cache);
    }
}
