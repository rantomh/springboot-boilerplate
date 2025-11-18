package com.rantomah.boilerplate.application.domain.entities;

import com.rantomah.boilerplate.application.domain.constant.OtpUsage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(
        name = "one_time_password",
        indexes = {@Index(name = "idx_otp_keycode", columnList = "key, code", unique = true)})
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private boolean used;

    private OtpUsage usage;

    @Column(nullable = false)
    private Instant expiresAt;

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
