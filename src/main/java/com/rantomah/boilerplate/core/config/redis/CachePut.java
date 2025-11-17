package com.rantomah.boilerplate.core.config.redis;

import java.lang.annotation.*;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.cache.annotation.CachePut(
        keyGenerator = "customKeyGenerator",
        condition = "#result != '__NOCACHE__'")
@Documented
public @interface CachePut {

    @AliasFor(annotation = org.springframework.cache.annotation.CachePut.class, attribute = "value")
    String value();
}
