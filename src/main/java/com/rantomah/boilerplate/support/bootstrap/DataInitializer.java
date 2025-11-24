package com.rantomah.boilerplate.support.bootstrap;

import com.rantomah.boilerplate.adapter.entity.User;
import com.rantomah.boilerplate.application.domain.constant.UserRole;
import com.rantomah.boilerplate.core.config.oauth2.RealmScope;
import com.rantomah.boilerplate.infrastructure.repository.user.UserRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {

    @Value("${application.security.oidc.clients.mobile}")
    private String mobileClientId;

    @Value("${application.security.oidc.redirect-uri}")
    private String redirectUri;

    @Value("${application.security.oidc.default-user}")
    private String defaultUser;

    @Value("${application.security.oidc.default-password}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            if (userRepository.findByUsername(defaultUser).isEmpty()) {
                User admin = new User();
                admin.setUsername(defaultUser);
                admin.setPassword(passwordEncoder.encode(defaultPassword));
                admin.setRole(UserRole.ADMIN);
                admin.setEnabled(true);
                userRepository.save(admin);
            }

            if (registeredClientRepository.findByClientId("mobile-app") == null) {
                RegisteredClient flutter =
                        RegisteredClient.withId(mobileClientId)
                                .clientId("mobile-app")
                                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                .redirectUri(redirectUri)
                                .scope(RealmScope.OPENID.value())
                                .scope(RealmScope.PROFILE.value())
                                .scope(RealmScope.APPLICATION.value())
                                .clientSettings(
                                        ClientSettings.builder()
                                                .requireProofKey(true)
                                                .requireAuthorizationConsent(true)
                                                .build())
                                .tokenSettings(
                                        TokenSettings.builder()
                                                .accessTokenTimeToLive(Duration.ofMinutes(15))
                                                .reuseRefreshTokens(true)
                                                .refreshTokenTimeToLive(Duration.ofDays(30))
                                                .build())
                                .build();
                registeredClientRepository.save(flutter);
            }
            log.info("########### init completed ###########");
        };
    }
}
