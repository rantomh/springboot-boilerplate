package com.rantomah.boilerplate.core.exception.handlers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * Handles cache-related errors by logging them without interrupting the application flow. This
 * implementation ensures that cache errors don't cause application failures while maintaining
 * visibility of issues through logging.
 */
public class CacheExceptionHandler implements CacheErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

    /**
     * Handles errors occurring during a cache get operation by logging the error details.
     *
     * @param e the RuntimeException that occurred during the cache get operation
     * @param cache the Cache on which the error occurred
     * @param key the key used for the cache get operation that caused the error
     */
    @Override
    public void handleCacheGetError(
            @NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
        logCacheError("get", e, cache, key);
    }

    /**
     * Handles errors that occur during a cache put operation by logging the error details. This
     * method ensures that the application flow is not interrupted due to cache-related issues while
     * providing visibility into the underlying error through logging.
     *
     * @param e the RuntimeException that occurred during the cache put operation
     * @param cache the Cache where the put operation was attempted
     * @param key the key associated with the attempted put operation
     * @param value the value that was attempted to be put into the cache
     */
    @Override
    public void handleCachePutError(
            @NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key, Object value) {
        logCacheError("put", e, cache, key);
    }

    /**
     * Handles errors that occur during a cache evict operation by logging the error details. This
     * method ensures that the application flow is not interrupted due to cache-related issues,
     * while providing visibility into the underlying error through logging.
     *
     * @param e the RuntimeException that occurred during the cache evict operation
     * @param cache the Cache where the evict operation was attempted
     * @param key the key associated with the attempted evict operation
     */
    @Override
    public void handleCacheEvictError(
            @NotNull RuntimeException e, @NotNull Cache cache, @NotNull Object key) {
        logCacheError("evict", e, cache, key);
    }

    /**
     * Handles errors that occur during a cache clear operation by logging the error details. This
     * method ensures that the application flow is not interrupted due to cache-related issues,
     * while providing visibility into the underlying error through logging.
     *
     * @param e the RuntimeException that occurred during the cache clear operation
     * @param cache the Cache on which the error occurred
     */
    @Override
    public void handleCacheClearError(@NotNull RuntimeException e, @NotNull Cache cache) {
        logCacheError("clear", e, cache, null);
    }

    /**
     * Logs errors related to cache operations, providing detailed information about the failed
     * operation, the involved cache, and the associated key, if applicable.
     *
     * @param operation the name of the cache operation that failed (e.g., "get", "put", "evict",
     *     "clear")
     * @param e the RuntimeException that occurred during the operation
     * @param cache the Cache instance where the error occurred
     * @param key the key associated with the cache operation that caused the error; it can be null
     */
    private void logCacheError(String operation, RuntimeException e, Cache cache, Object key) {
        String keyInfo = key != null ? String.format(" with key [%s]", key) : "";
        logger.warn(
                "Cache {} operation failed for cache [{}]{}. Error: {}",
                operation,
                cache.getName(),
                keyInfo,
                e.getMessage());
    }
}
