package com.rantomah.boilerplate.infrastructure.repository;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.entity.OTP;
import com.rantomah.boilerplate.core.GenericRepository;
import java.util.Optional;

public interface OtpRepository extends GenericRepository<OTP, Long> {

    Optional<OTP> findByCodeAndCleAndUsage(String code, String key, OtpUsage usage);

    Optional<OTP> findByCodeAndCle(String code, String key);
}
