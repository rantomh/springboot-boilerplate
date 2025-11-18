package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.infrastructure.validator.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDTO {

    @NotNull @NotBlank private String oldPassword;

    @NotNull @Password private String newPassword;
}
