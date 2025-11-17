package com.rantomah.boilerplate.application.dto.user;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Data;

@Data
public class UserSearchDTO {

    private String username;

    @Valid private List<String> roles;
    private Boolean enabled;
    private Boolean locked;
}
