package com.rantomah.boilerplate.adapter.helper;

import com.rantomah.boilerplate.application.domain.dto.auth.LoginResponseDTO;
import com.rantomah.boilerplate.application.domain.entity.RefreshToken;
import com.rantomah.boilerplate.application.domain.entity.User;
import com.rantomah.boilerplate.core.config.oauth2.RealmScope;
import com.rantomah.boilerplate.core.config.oauth2.TokenType;
import com.rantomah.boilerplate.infrastructure.repository.RefreshTokenRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenHelper {

    private static final String PRIVILEGES_CLAIM = "privileges";
    private static final String SCOPE_CLAIM = "scope";
    private static final String JTI_CLAIM = "jti";

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-validity-seconds}")
    private long refreshValiditySeconds;

    @Value("${jwt.access-token-validity-seconds}")
    private long accessValiditySeconds;

    @Value("${application.security.oidc.issuer}")
    private String issuer;

    @Value("${jwt.access-token-validity-seconds}")
    private Long accessTokenValidity;

    protected String buildAccessTokenFromUser(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(issuer)
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(accessValiditySeconds))
                        .notBefore(now)
                        .audience(List.of("mobile-app"))
                        .subject(user.getId().toString())
                        .claim("username", user.getUsername())
                        .claim(
                                PRIVILEGES_CLAIM,
                                user.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                        .claim(
                                SCOPE_CLAIM,
                                List.of(
                                        RealmScope.OPENID.value(),
                                        RealmScope.PROFILE.value(),
                                        RealmScope.APPLICATION.value()))
                        .claim(JTI_CLAIM, UUID.randomUUID().toString())
                        .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    protected RefreshToken buildAndSaveRefreshTokenFromUser(User user) {
        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .user(user)
                        .expiresAt(Instant.now().plusSeconds(refreshValiditySeconds))
                        .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public LoginResponseDTO tokenWithRefresh(User user) {
        String accessToken = buildAccessTokenFromUser(user);
        RefreshToken refreshToken = buildAndSaveRefreshTokenFromUser(user);
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .tokenType(TokenType.BEARER)
                .refreshToken(refreshToken.getToken())
                .expiresIn(accessTokenValidity)
                .scope(RealmScope.APPLICATION)
                .build();
    }

    public LoginResponseDTO token(User user) {
        String accessToken = buildAccessTokenFromUser(user);
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .tokenType(TokenType.BEARER)
                .expiresIn(accessTokenValidity)
                .scope(RealmScope.APPLICATION)
                .build();
    }
}
