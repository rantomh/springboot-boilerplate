package com.rantomah.boilerplate.application.dto.user;

import com.rantomah.boilerplate.application.validator.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDTO {

    @NotNull @NotBlank private String oldPassword;

    @NotNull @Password private String newPassword;
}
