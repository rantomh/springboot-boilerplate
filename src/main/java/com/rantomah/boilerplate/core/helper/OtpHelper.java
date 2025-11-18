package com.rantomah.boilerplate.core.helper;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.entities.OTP;
import com.rantomah.boilerplate.core.exception.InvalidOtpException;
import com.rantomah.boilerplate.core.util.StringUtils;
import com.rantomah.boilerplate.infrastructure.repository.OtpRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpHelper {

    private final OtpRepository otpRepository;

    @Value("${application.security.otp.exiration:900}") // default 15 min
    private Long otpExpiration;

    @Transactional
    public OTP createByKeyAndUsage(String key, OtpUsage usage) {
        OTP otp =
                OTP.builder()
                        .key(key)
                        .code(StringUtils.generateActivationCode(6))
                        .usage(usage)
                        .expiresAt(Instant.now().plusSeconds(otpExpiration))
                        .build();
        return otpRepository.save(otp);
    }

    @Transactional
    public void processByCodeAndKey(String code, String key) {
        processByCodeAndKeyAndUsage(code, key, null);
    }

    @Transactional
    public void processByCodeAndKeyAndUsage(String code, String key, OtpUsage usage) {
        OTP otp;
        if (usage != null) {
            otp =
                    otpRepository
                            .findByCodeAndKeyAndUsage(code, key, usage)
                            .orElseThrow(InvalidOtpException::new);
        } else {
            otp = otpRepository.findByCodeAndKey(code, key).orElseThrow(InvalidOtpException::new);
        }
        if (otp.isUsed() || otp.isExpired()) {
            throw new InvalidOtpException();
        }
        otp.setUsed(true);
        otpRepository.save(otp);
    }
}
