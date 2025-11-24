package com.rantomah.boilerplate.core.config.redis;

import com.rantomah.boilerplate.core.exception.handler.CacheExceptionHandler;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig implements CachingConfigurer {

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheExceptionHandler();
    }
}
