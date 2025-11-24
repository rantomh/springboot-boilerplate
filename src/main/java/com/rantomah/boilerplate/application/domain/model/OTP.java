package com.rantomah.boilerplate.application.domain.model;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import java.time.Instant;

public interface OTP {

    Long getId();

    void setId(Long id);

    String getCle();

    void setCle(String cle);

    String getCode();

    void setCode(String code);

    boolean isUsed();

    void setUsed(boolean used);

    OtpUsage getUsage();

    void setUsage(OtpUsage usage);

    Instant getExpiresAt();

    void setExpiresAt(Instant expiresAt);

    boolean isExpired();
}
