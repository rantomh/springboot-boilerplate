package com.rantomah.boilerplate.core.config.oauth2;

public enum RealmScope {

    // standart scope
    OPENID("openid"),
    PROFILE("profile"),
    EMAIL("email"),
    ADDRESS("address"),
    PHONE("phone"),
    OFFLINE_ACCESS("offline_access"),

    // custom scope
    USER("user"),
    SALES("sales"),
    APPLICATION("application"); // all services

    private final String value;

    RealmScope(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
