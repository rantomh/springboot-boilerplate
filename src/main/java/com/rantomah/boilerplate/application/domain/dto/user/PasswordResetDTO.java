package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.core.validator.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetDTO {

    @NotNull @Password private String password;

    @NotNull private String otp;

    @NotBlank @NotNull private String key;
}
