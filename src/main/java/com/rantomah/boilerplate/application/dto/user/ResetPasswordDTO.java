package com.rantomah.boilerplate.application.dto.user;

import com.rantomah.boilerplate.application.validator.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordDTO {

    @NotNull @Password private String password;

    @NotNull private String otp;

    @NotBlank @NotNull private String key;
}
