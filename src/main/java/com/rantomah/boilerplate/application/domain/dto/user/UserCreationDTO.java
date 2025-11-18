package com.rantomah.boilerplate.application.domain.dto.user;

import com.rantomah.boilerplate.application.domain.constant.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreationDTO {

    @Email @NotNull private String email;

    @NotNull @Valid private UserRole role;
}
