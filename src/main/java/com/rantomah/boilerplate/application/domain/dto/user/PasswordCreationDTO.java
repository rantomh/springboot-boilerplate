package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.core.validator.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordCreationDTO {

    @NotNull @Password private String password;
}
