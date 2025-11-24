package com.rantomah.boilerplate.support.helper;

import com.rantomah.boilerplate.adapter.dao.OtpDAO;
import com.rantomah.boilerplate.adapter.entity.OTPEntity;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.model.OTP;
import com.rantomah.boilerplate.core.exception.InvalidOtpException;
import com.rantomah.boilerplate.core.util.StringUtils;
import jakarta.transaction.Transactional;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpHelper {

    private final OtpDAO otpDAO;

    @Value("${application.security.otp.exiration:900}") // default 15 min
    private Long otpExpiration;

    @Transactional
    public OTP createByKeyAndUsage(String cle, OtpUsage usage) {
        OTPEntity otp =
                OTPEntity.builder()
                        .cle(cle)
                        .code(StringUtils.generateActivationCode(6))
                        .usage(usage)
                        .expiresAt(Instant.now().plusSeconds(otpExpiration))
                        .build();
        return otpDAO.save(otp);
    }

    @Transactional
    public void processByCodeAndKey(String code, String key) {
        processByCodeAndKeyAndUsage(code, key, null);
    }

    @Transactional
    public void processByCodeAndKeyAndUsage(String code, String cle, OtpUsage usage) {
        OTP otp;
        if (usage != null) {
            otp =
                    otpDAO.findByCodeAndCleAndUsage(code, cle, usage)
                            .orElseThrow(InvalidOtpException::new);
        } else {
            otp = otpDAO.findByCodeAndCle(code, cle).orElseThrow(InvalidOtpException::new);
        }
        if (otp.isUsed() || otp.isExpired()) {
            throw new InvalidOtpException();
        }
        otp.setUsed(true);
        otpDAO.save(otp);
    }
}
