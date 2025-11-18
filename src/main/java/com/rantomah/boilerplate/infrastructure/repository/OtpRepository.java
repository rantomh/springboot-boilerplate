package com.rantomah.boilerplate.infrastructure.repository;

import com.rantomah.boilerplate.core.GenericRepository;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.entities.OTP;
import java.util.Optional;

public interface OtpRepository extends GenericRepository<OTP, Long> {

    Optional<OTP> findByCodeAndKeyAndUsage(String code, String key, OtpUsage usage);

    Optional<OTP> findByCodeAndKey(String code, String key);
}
