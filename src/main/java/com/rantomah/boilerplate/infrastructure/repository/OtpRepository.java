package com.rantomah.boilerplate.infrastructure.repository;

import com.rantomah.boilerplate.adapter.entity.OTPEntity;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.core.GenericRepository;
import java.util.Optional;

public interface OtpRepository extends GenericRepository<OTPEntity, Long> {

    Optional<OTPEntity> findByCodeAndCleAndUsage(String code, String key, OtpUsage usage);

    Optional<OTPEntity> findByCodeAndCle(String code, String key);
}
