package com.rantomah.boilerplate.application.dto.user;

import com.rantomah.boilerplate.application.validator.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePasswordDTO {

    @NotNull @Password private String password;
}
