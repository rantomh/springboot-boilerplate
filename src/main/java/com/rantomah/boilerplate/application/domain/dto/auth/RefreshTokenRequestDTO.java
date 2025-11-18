package com.rantomah.boilerplate.application.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RefreshTokenRequestDTO {

    @NotBlank @NotNull private String refreshToken;
}
