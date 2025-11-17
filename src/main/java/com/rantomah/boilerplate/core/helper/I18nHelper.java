package com.rantomah.boilerplate.core.helper;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class I18nHelper {

    private final ResourceBundleMessageSource messageSource;

    public String get(String key) {
        return resolve(key, currentLocale(), null);
    }

    public String get(String key, Object... args) {
        return resolve(key, currentLocale(), args);
    }

    public String get(String key, Locale locale) {
        return resolve(key, locale, null);
    }

    public String get(String key, Object[] args, Locale locale) {
        return resolve(key, locale, args);
    }

    public String get(String key, Locale locale, Object... args) {
        return resolve(key, locale, args);
    }

    private Locale currentLocale() {
        return LocaleContextHolder.getLocale();
    }

    private String resolve(String key, Locale locale, Object[] args) {
        return messageSource.getMessage(key, args, locale);
    }
}
