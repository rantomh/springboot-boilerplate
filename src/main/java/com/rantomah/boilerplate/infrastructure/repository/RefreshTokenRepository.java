package com.rantomah.boilerplate.infrastructure.repository;

import com.rantomah.boilerplate.core.GenericRepository;
import com.rantomah.boilerplate.application.domain.entities.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends GenericRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
