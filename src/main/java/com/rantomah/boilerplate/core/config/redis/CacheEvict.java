package com.rantomah.boilerplate.core.config.redis;

import java.lang.annotation.*;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.cache.annotation.CacheEvict(keyGenerator = "customKeyGenerator")
@Documented
public @interface CacheEvict {

    @AliasFor(
            annotation = org.springframework.cache.annotation.CacheEvict.class,
            attribute = "value")
    String value();

    @AliasFor(
            annotation = org.springframework.cache.annotation.CacheEvict.class,
            attribute = "allEntries")
    boolean allEntries() default false;
}
