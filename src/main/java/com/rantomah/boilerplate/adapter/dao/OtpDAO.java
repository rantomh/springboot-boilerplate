package com.rantomah.boilerplate.adapter.dao;

import com.rantomah.boilerplate.adapter.entity.OTPEntity;
import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import com.rantomah.boilerplate.application.domain.model.OTP;
import com.rantomah.boilerplate.application.port.output.OTPOutputPort;
import com.rantomah.boilerplate.infrastructure.repository.OtpRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OtpDAO implements OTPOutputPort {

    private final OtpRepository otpRepository;

    @Override
    public Optional<OTP> findByCodeAndCleAndUsage(String code, String key, OtpUsage usage) {
        return otpRepository.findByCodeAndCleAndUsage(code, key, usage).map(o -> (OTP) o);
    }

    @Override
    public Optional<OTP> findByCodeAndCle(String code, String key) {
        return otpRepository.findByCodeAndCle(code, key).map(o -> (OTP) o);
    }

    @Override
    public OTP save(OTP model) {
        return otpRepository.save((OTPEntity) model);
    }
}
