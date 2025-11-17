package com.rantomah.boilerplate.core.config.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.cache.annotation.Cacheable(
        keyGenerator = "customKeyGenerator",
        condition = "#result != '__NOCACHE__'")
@Documented
public @interface Cacheable {

    @AliasFor(
            annotation = org.springframework.cache.annotation.Cacheable.class,
            attribute = "value")
    String value();
}
