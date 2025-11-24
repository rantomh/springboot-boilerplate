package com.rantomah.boilerplate.application.port.output;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.model.OTP;
import java.util.Optional;

public interface OTPOutputPort {

    Optional<OTP> findByCodeAndCleAndUsage(String code, String key, OtpUsage usage);

    Optional<OTP> findByCodeAndCle(String code, String key);

    OTP save(OTP model);
}
