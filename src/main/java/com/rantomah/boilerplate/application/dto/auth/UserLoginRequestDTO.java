package com.rantomah.boilerplate.application.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UserLoginRequestDTO {

    @NotBlank @NotNull private String username;

    @NotBlank @NotNull private String password;
}
