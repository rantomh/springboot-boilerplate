package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.infrastructure.validator.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePasswordDTO {

    @NotNull @Password private String password;
}
