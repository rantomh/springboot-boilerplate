package com.rantomah.boilerplate.adapters.out.repository;

import com.rantomah.boilerplate.core.GenericRepository;
import com.rantomah.boilerplate.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository extends GenericRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
