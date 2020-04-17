package com.redis.tutorial.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        LOG.error("Unable to fetch data from cache for cache {0} and key {1}", cache.getName(), key.toString(), exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        LOG.error("Unable to update data to cache for cache {0} and key {1}", cache.getName(), key.toString(), exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        LOG.error("Unable to evict cache for cache {0} and key {1}", cache.getName(), key.toString(), exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        LOG.error("Unable to clear cache from cache for cache {0}", cache.getName(), exception);
    }
}
