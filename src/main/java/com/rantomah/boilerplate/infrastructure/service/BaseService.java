package com.rantomah.boilerplate.infrastructure.service;

import com.rantomah.boilerplate.application.domain.entities.User;
import com.rantomah.boilerplate.core.exception.AuthenticationException;
import com.rantomah.boilerplate.core.exception.UserDisabledException;
import com.rantomah.boilerplate.core.exception.UserLockedException;
import com.rantomah.boilerplate.core.helper.OtpHelper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService {

    @Autowired protected ApplicationEventPublisher publisher;

    @Autowired protected PasswordEncoder passwordEncoder;

    @Autowired protected OtpHelper otpHelper;

    protected void validateUser(User user) {
        if (!user.isEnabled()) {
            throw new UserDisabledException();
        }
        if (!user.isAccountNonLocked()) {
            throw new UserLockedException();
        }
    }

    protected UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken)) {
            throw new AuthenticationException();
        }
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) auth;
        String userId = jwtAuth.getToken().getClaimAsString("sub");
        if (userId == null) {
            throw new AuthenticationException();
        }
        return UUID.fromString(userId);
    }
}
