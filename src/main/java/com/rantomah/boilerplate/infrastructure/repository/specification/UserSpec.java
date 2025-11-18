package com.rantomah.boilerplate.infrastructure.repository.specification;

import com.rantomah.boilerplate.application.domain.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {

    public static Specification<User> hasUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> isEnabled(Boolean enabled) {
        if (enabled == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("enabled"), enabled);
    }

    public static Specification<User> isLocked(Boolean locked) {
        if (locked == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("locked"), locked);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Specification<User> spec;

        public Builder() {
            spec = Specification.where(null);
        }

        public Builder username(String username) {
            Specification<User> s = hasUsername(username);
            if (s != null) {
                spec = spec.and(s);
            }
            return this;
        }

        public Builder enabled(Boolean enabled) {
            Specification<User> s = isEnabled(enabled);
            if (s != null) {
                spec = spec.and(s);
            }
            return this;
        }

        public Builder locked(Boolean locked) {
            Specification<User> s = isLocked(locked);
            if (s != null) {
                spec = spec.and(s);
            }
            return this;
        }

        public Specification<User> build() {
            return spec;
        }
    }
}
